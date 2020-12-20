package org.inria.restlet.mta.application;


import org.inria.restlet.mta.resources.*;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * created by Bonaventure Gbehe && Rebecca Ehua
 */
public class OceanApplication extends Application
{
    public OceanApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/zones", ZonesResource.class);
        router.attach("/zones/{zoneId}", ZoneResource.class);
        router.attach("/tunas", SardineResource.class);
        router.attach("/sharks", RequinsResource.class);
        router.attach("/sharks/{requinId}", RequinResource.class);

        return router;
    }
}
