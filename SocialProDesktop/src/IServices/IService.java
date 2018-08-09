/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;

/**
 *
 * @author Mortadhafff
 */
public interface IService<T, R> {
    
    void add(T t);
    
    void update(int id,T b);
    void delete(R r);
    
    T findById(R r);
    
    List<T> getAll();
}
