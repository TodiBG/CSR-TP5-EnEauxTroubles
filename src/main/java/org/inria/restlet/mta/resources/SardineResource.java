package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.backend.Zone;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * created by Bonaventure Gbehe && Rebecca Ehua
 */
public class SardineResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public SardineResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes()
            .get("backend");
    }

    @Get("json")
    public Representation getZone() throws Exception
    {
        List<Zone> zones = backend_.getOcean().getListZones();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        int totalSardine =  0 ;

        for (Zone zone : zones){  totalSardine += zone.getNb_sardines() ; }

        JSONObject totalSardineObject = new JSONObject();
        totalSardineObject.put("Total_Sardine", totalSardine);

        return new JsonRepresentation(totalSardineObject);
    }




}
