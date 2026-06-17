package com.grupo7.ticket_system.sales;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.grupo7.ticket_system.models.RequestSale;
import com.grupo7.ticket_system.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sales")
public class SaleController {
    
    private final SaleService saleService;

    SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/createSale")
    public Sale createSale(@RequestBody RequestSale requestSale){
        return saleService.createSale(requestSale);
    }
}
