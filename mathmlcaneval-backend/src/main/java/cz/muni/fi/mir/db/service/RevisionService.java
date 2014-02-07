/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.Revision;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface RevisionService
{
    void createRevision(Revision revision);
    void deleteRevision(Revision revision);
    void updateRevision(Revision revision);
    
    Revision getRevisionByID(Long id);
    Revision getRevisionByHash(String hash);
    
    
    List<Revision> getAllRevisions(); 
    List<Revision> findRevisionByNote(String note);
}