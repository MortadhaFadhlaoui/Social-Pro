/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.util.StringConverter;

/**
 *
 * @author Mortadhafff
 */
public class CopetenceConverter extends StringConverter<Competence>{

    @Override
    public String toString(Competence competence) {
       if (competence == null) {
            return null;
        } else {
            return competence.getNom();
        }
    }

    @Override
    public Competence fromString(String string) {
      Competence competence = null; 
        if (string == null)
        {
            return competence;
        }        
        else
        {
            competence = new Competence(string);
        }
        return competence;
    }
    
}
