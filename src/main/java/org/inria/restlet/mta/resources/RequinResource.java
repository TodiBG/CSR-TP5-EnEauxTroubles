package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.backend.Requin;
import org.inria.restlet.mta.backend.Zone;
import org.inria.restlet.mta.internals.User;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * created by Bonaventure Gbehe && Rebecca Ehua
 */
public class RequinResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public RequinResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes()
            .get("backend");
    }

    @Get("json")
    public Representation getZone() throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("requinId");
        int reqId = Integer.valueOf(userIdString);
        Requin requin = backend_.getOcean().getRequinById(reqId) ;

        JSONObject userObject = new JSONObject();
        if(requin != null){
            userObject.put("id", "R"+requin.getIdReq());

            userObject.put("vieRestant", requin.getCycleDeVie());
            userObject.put("nb_pilotesActuelle", requin.getNbPilote());

            String zone = "null" ;
            if(requin.getZone() != null){ zone =  requin.getZone().getNom() ;   }
            userObject.put("zoneActuelle", zone);
        }
        return new JsonRepresentation(userObject);
    }

}
