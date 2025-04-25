package services.account.api;

class PageInfo {
    int pageNum;
    int pageSize;
    
    PageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
