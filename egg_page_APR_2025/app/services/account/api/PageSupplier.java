package services.account.api;

public interface PageSupplier<T> {
    Page<T> nextPage() throws Exception;
}
