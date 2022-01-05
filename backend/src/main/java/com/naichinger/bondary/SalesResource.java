package com.naichinger.bondary;

import com.naichinger.control.ProductRepository;
import com.naichinger.control.SalesRepository;
import com.naichinger.entity.Product;
import com.naichinger.entity.Receipt;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class SalesResource {
    @Inject
    SalesRepository salesRepository;

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Receipt> getAllReceipts() {
        return salesRepository.findAll();
    }
}
