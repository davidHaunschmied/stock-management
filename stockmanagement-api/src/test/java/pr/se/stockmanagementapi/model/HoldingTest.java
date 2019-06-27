package pr.se.stockmanagementapi.model;

import org.junit.Before;
import org.junit.Test;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HoldingTest {

    private Holding holding;

    @Before
    public void setup() {
        this.holding = new Holding(mock(Depot.class), mock(Stock.class));
    }

    @Test
    public void testAddPurchase() {
        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(10);
        when(transaction.getDate()).thenReturn(new Date());
        when(transaction.getPrice()).thenReturn(60.50);
        when(transaction.getTransactionType()).thenReturn(TransactionType.PURCHASE);

        holding.addTransaction(transaction);
        assertThat(holding.getAmount()).isEqualTo(10);
        assertThat(holding.getTotalPrice()).isEqualTo(60.50);
    }

    @Test
    public void testAddPurchaseToExisting() {
        holding.setAmount(10);
        holding.setTotalPrice(70);
        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(5);
        when(transaction.getDate()).thenReturn(new Date());
        when(transaction.getPrice()).thenReturn(30.1);
        when(transaction.getTransactionType()).thenReturn(TransactionType.PURCHASE);

        holding.addTransaction(transaction);
        assertThat(holding.getAmount()).isEqualTo(15);
        assertThat(holding.getTotalPrice()).isEqualTo(100.1);
    }

    @Test
    public void testAddSell() {
        holding.setAmount(10);
        holding.setTotalPrice(70);
        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(5);
        when(transaction.getDate()).thenReturn(new Date());
        when(transaction.getPrice()).thenReturn(80.0);
        when(transaction.getTransactionType()).thenReturn(TransactionType.SALE);

        holding.addTransaction(transaction);
        assertThat(holding.getAmount()).isEqualTo(5);
        assertThat(holding.getTotalPrice()).isEqualTo(35);
    }

    @Test
    public void testAddSellAll() {
        holding.setAmount(10);
        holding.setTotalPrice(70);
        Transaction transaction = mock(Transaction.class);
        when(transaction.getAmount()).thenReturn(10);
        when(transaction.getDate()).thenReturn(new Date());
        when(transaction.getPrice()).thenReturn(90.0);
        when(transaction.getTransactionType()).thenReturn(TransactionType.SALE);

        holding.addTransaction(transaction);
        assertThat(holding.getAmount()).isEqualTo(0);
        assertThat(holding.getTotalPrice()).isEqualTo(0);
    }
}
