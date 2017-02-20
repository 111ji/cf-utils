package cn.careerforce.web;

public class PageResponse extends Response implements java.io.Serializable
{
    private static final long serialVersionUID = -412095459773582659L;

    private int totalRow = 0;
    private int pageSize = 15;
    private int pageNumber = 1;
    private int totalPage = 1;

    public PageResponse()
    {
        super();
    }

    public PageResponse(Object msg)
    {
        super(msg);
    }

    public PageResponse(int res, Object data)
    {
        super(res, data);
    }

    public PageResponse(String msg, Object data)
    {
        super(msg, data);
    }


    public int getTotalRow()
    {
        return totalRow;
    }

    public void setTotalRow(int totalRow)
    {
        this.totalRow = totalRow;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public int getTotalPage()
    {
        return totalPage;
    }

    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }


    public void initPageInfo(int totalRow, int pageSize, int pageNumber)
    {
        this.totalRow = totalRow;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
    }

}
