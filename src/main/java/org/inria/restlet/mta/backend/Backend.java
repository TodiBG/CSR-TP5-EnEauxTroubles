package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.api.impl.Ocean;

public class Backend
{
    private static Backend instance;
    public static Backend getInstance (){
        if( instance == null){ instance = new Backend() ;  }
        return  instance ;
    }

    public Ocean getOcean(){ return  Ocean.getInstance() ; }
}
