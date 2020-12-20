package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.inria.restlet.mta.backend.Zone;
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
public class ZonesResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ZonesResource()
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
        List<Zone> zones = backend_.getOcean().getListZones();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (Zone zone : zones)
        {
            JSONObject current = new JSONObject();
            current.put("id", zone.getId());
            current.put("nom", zone.getNom());
            current.put("url", getReference() + "/" + zone.getId());
            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }

}
