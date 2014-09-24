package cz.muni.fi.mir.scheduling;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ElementService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.scheduling.TaskStatus.TaskType;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.tools.XMLUtils;

/**
 * TODO turn mass import to task
 *
 */
public class FormulaImportTask extends ApplicationTask
{
    private User user;
    private Revision revision;
    private Configuration configuration;
    private Program program;
    private SourceDocument sourceDocument;
    private String filter;
    private String path;

    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private FormulaService formulaService;
    @Autowired
    private FileDirectoryService fileDirectoryService;
    @Autowired
    private ApplicationRunService applicationRunService;
    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;
    @Autowired
    private ElementService elementService;
    @Autowired
    private XMLUtils xmlUtils;

    private static final Logger logger = Logger.getLogger(FormulaImportTask.class);

    /**
     * Method setups data dependencies used for FormulaImportTask.
     */
    public void setDependencies(String path, String filter, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument, User user)
    {
        if(user == null)
        {
            throw new IllegalArgumentException("User is null");
        }

        this.user = user;
        this.revision = revision;
        this.configuration = configuration;
        this.program = program;
        this.sourceDocument = sourceDocument;
        this.path = path;
        this.filter = filter;

        setStatus(new TaskStatus());
        getStatus().setTaskType(TaskType.massImport);
    }

    @Override
    public TaskStatus call() throws Exception
    {
        ApplicationRun applicationRun = EntityFactory.createApplicationRun();
        applicationRun.setUser(user);
        logger.info(applicationRun.getUser());
        applicationRun.setRevision(revision);
        applicationRun.setConfiguration(configuration);

        List<Formula> toImport = Collections.emptyList();
        try
        {
            toImport = fileDirectoryService.exploreDirectory(path, filter);
        }
        catch (FileNotFoundException ex)
        {
            logger.error(ex);
        }
        if (!toImport.isEmpty())
        {
            getStatus().setTotal(toImport.size());
            getStatus().setCurrent(0);
            getStatus().setUser(user);
            getStatus().setNote(path);
            DateTime startTime = DateTime.now();
            getStatus().setStartTime(startTime);

            logger.fatal("Attempt to create Application Run with flush mode to ensure its persisted.");
            applicationRunService.createApplicationRunWithFlush(applicationRun);
            logger.fatal("Operation withFlush called.");

            List<Formula> filtered = new ArrayList<>();
            for (Formula f : toImport)
            {
                String hash = Tools.getInstance().SHA1(f.getXml());
                Long id = formulaDAO.exists(hash);
                if (id == null)
                {
                    f.setHashValue(hash);
                    f.setProgram(program);
                    f.setUser(user);
                    f.setSourceDocument(sourceDocument);

                    extractElements(f);
                    attachElements(f);
                    formulaService.createFormula(f);

                    filtered.add(f);

                    getStatus().setCurrent(getStatus().getCurrent() + 1);
                }
                else
                {
                    logger.info("Formula already exists with ID [" + id + "] - skipping.");
                }
            }

            if(filtered.isEmpty())
            {
                logger.warn("No formulas are going to be imported because they are already presented.");
            }

            DateTime stopTime = DateTime.now();
            getStatus().setStopTime(stopTime);

            mathCanonicalizerLoader.execute(filtered, applicationRun);
        }

        return getStatus();
    }

    // TODO refactor.
    // Move these methods elsewhere. They're needed in formulaService and here as well.
    private void extractElements(Formula f)
    {
        Set<Element> temp = new HashSet<>();

        org.w3c.dom.Document doc = xmlUtils.parse(f.getXml());

        if (doc != null)
        {
            org.w3c.dom.NodeList nodeList = doc.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                temp.add(EntityFactory.createElement(nodeList.item(i).getNodeName()));
            }
        }
        List<Element> result = null;

        if (f.getElements() == null || f.getElements().isEmpty())
        {
            result = new ArrayList<>(temp.size());
        }
        else
        {
            result = new ArrayList<>(f.getElements());
        }

        result.addAll(temp);

        f.setElements(result);
    }

    private void checkNull(Formula f) throws IllegalArgumentException
    {
        if (f == null)
        {
            throw new IllegalArgumentException("Given formula is null.");
        }
    }

    /**
     * Method takes elements from formula and matches them against already
     * persisted list of elements. If element already exist then it has id in
     * obtained list (from database) and id for element in formula is set.
     * Otherwise we check temp list which contains newly created elements. If
     * there is no match then new element is created and stored in temp list.
     * Equals method somehow fails on CascadeType.ALL, so this is reason why we
     * have to do manually. TODO redo in future. Possible solution would be to
     * have all possible elements already stored inside database.
     *
     * @param f formula of which we attach elements.
     */
    private void attachElements(Formula f)
    {
        if (f.getElements() != null && !f.getElements().isEmpty())
        {
            List<Element> list = elementService.getAllElements();
            List<Element> newList = new ArrayList<>();
            for (Element e : f.getElements())
            {
                int index = list.indexOf(e);
                if (index == -1)
                {
                    int index2 = newList.indexOf(e);
                    if (index2 == -1)
                    {
                        elementService.createElement(e);
                        newList.add(e);
                    }
                    else
                    {
                        e.setId(newList.get(index2).getId());
                    }
                }
                else
                {
                    e.setId(list.get(index).getId());
                }
            }
        }
    }
}
