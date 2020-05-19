package jp.co.xq.base.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 改ページ処理ツール
 *
 * @author tian w
 */
public class PageUtils implements Serializable {

    /**
     * ディフォルト　一ページデータ数
     **/
    private static final int PAGE_SIZE = 10;
    /**
     * ディフォルト　現在ページ
     **/
    private static final int CURRENT_PAGE = 1;

    /**
     * すべてのデータ数
     **/
    private long totalCount;
    /**
     * 一ページデータ数
     **/
    private int pageSize;
    /**
     * 総数
     **/
    private int totalPage;
    /**
     * 現在ページ
     **/
    private int currPage;
    /**
     * データリスト
     **/
    private List<?> list;

    /**
     * 改ページ
     *
     * @param list       データリスト
     * @param totalCount データ総数
     * @param pageSize   一ページデータ数
     * @param currPage   現在ページ
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 改ページ
     *
     * @param list       データリスト
     * @param totalCount データ総数
     * @param map        条件Map
     */
    public PageUtils(List<?> list, long totalCount, Map map) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = map.get("limit") == null ? PAGE_SIZE : Integer.parseInt(map.get("limit").toString());
        this.currPage = map.get("page") == null ? CURRENT_PAGE : Integer.parseInt(map.get("page").toString());
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }


    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
