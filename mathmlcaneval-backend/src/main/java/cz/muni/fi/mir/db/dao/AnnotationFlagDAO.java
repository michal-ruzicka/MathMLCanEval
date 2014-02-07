package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.AnnotationFlag;
import java.util.List;

/**
 *  The purpose of this interface is to provide basic CRUD operations and search 
 * functionality on AnnotationFlag objects persisted inside any given database engine 
 * specified by configuration. Since there might be some functionality that requires
 * more operation calls, no transaction management should be managed on this layer.
 * Also no validation is made on this layer so make sure you do not pass non-valid
 * objects into implementation of this DAO (Database Access Object) layer.
 * 
 * @author Dominik Szalai
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public interface AnnotationFlagDAO
{
    /**
     * Method creates AnnotationFlag inside database.
     * @param annotationFlag to be persisted
     */
    void createFlagAnnotation(AnnotationFlag annotationFlag);
    
    /**
     * Method updates given AnnotationFlag inside database.
     * @param annotationFlag to be updated
     */
    void updateFlagAnnotation(AnnotationFlag annotationFlag);
    
    /**
     * Method deletes given AnnotationFlag from database. Sine entityManger
     * checks only ID, its the only required field to be set before deletion.
     * @param annotationFlag to be deleted
     */
    void deleteFlagAnnotation(AnnotationFlag annotationFlag);
    
    /**
     * Method fetches AnnotationFlag from database based on given ID.
     * @param id of Annotation to be fetched.
     * @return Annotation with given ID, null if there is no match.
     */
    AnnotationFlag getAnnotationFlagByID(Long id);   
    
    /**
     * Method obtains all AnnotationFlags from database in <b>DESCENDING</b> order.
     * @return List of all AnnotationFlags, empty List if there are no 
     * AnnotationFlags stored yet.
     */
    List<AnnotationFlag> getAllAnnotationFlags();
    
    /**
     * Method find all AnnotationFlags with given value.
     * @param value to be found in value field of AnnotationFlag
     * @return List of AnnotationFlags containing given value, empty List
     * if there is no match.
     */
    List<AnnotationFlag> findAnnotationFlagByValue(String value);    
}