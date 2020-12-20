package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.backend.Requin;
import org.inria.restlet.mta.backend.Zone;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * created by Bonaventure Gbehe && Rebecca Ehua
 */
public class RequinsResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public RequinsResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes()
            .get("backend");
    }

    /**
     *
     * Returns the list of all the users
     *
     * @return  JSON representation of the users
     * @throws JSONException
     */
    @Get("json")
    public Representation getZones() throws JSONException
    {
        List<Requin> requins = backend_.getOcean().getRequins();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (Requin req : requins)
        {
            JSONObject current = new JSONObject();
            current.put("id", req.getIdReq());
            current.put("url", getReference() + "/" + req.getIdReq());

            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }



    @Post("json")
    public Representation createRequin(JsonRepresentation representation)throws Exception
    {
        JSONObject object = representation.getJsonObject();
        int vie = object.getInt("vie");

        Requin requin = backend_.getOcean().createRequin(vie);
        System.out.println(requin);

        // generate result
        JSONObject resultObject = new JSONObject();
        if(requin != null){
            resultObject.put("id", "R"+requin.getIdReq());

            resultObject.put("vieRestant", requin.getCycleDeVie());
            resultObject.put("nb_pilotesActuelle", requin.getNbPilote());

            String zone = "null" ;
            if(requin.getZone() != null){ zone =  requin.getZone().getNom() ;   }
            resultObject.put("zoneActuelle", zone);
        }
        return new JsonRepresentation(resultObject);
    }

}
