package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.respository.StockRepository;

@Service
public class StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock findStockByIdOrThrow(long id) {
        return stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock with id " + id + " not found!"));
    }
}
