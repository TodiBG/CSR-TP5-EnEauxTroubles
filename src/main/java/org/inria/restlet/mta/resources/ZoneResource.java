package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.inria.restlet.mta.backend.Zone;
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
public class ZoneResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ZoneResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes()
            .get("backend");
    }

    @Get("json")
    public Representation getZone() throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("zoneId");
        int userId = Integer.valueOf(userIdString);
        Zone zone = backend_.getOcean().getZoneById(userId) ;

        JSONObject userObject = new JSONObject();
        if(zone != null){
            userObject.put("id", zone.getId());
            userObject.put("axe_X", zone.getX());
            userObject.put("axe_Y", zone.getY());
            userObject.put("nbSardines", zone.getNb_sardines());

            String reg_name = "null" ;
            if(zone.getRequin() != null){ reg_name =  zone.getRequin().getNom() ;   }
            userObject.put("requin", reg_name);
        }
        return new JsonRepresentation(userObject);
    }

}
