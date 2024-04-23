package team.CowsAndHorses.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import team.CowsAndHorses.dto.PageQueryDto;

import java.util.List;

public class PageUtil {
    public static <T> IPage<T> List2IPage(List<T> resultList, Integer pageNum, Integer pageSize) {
        IPage<T> iPage = new Page<>();
        iPage.setTotal(resultList.size());
        iPage.setCurrent(pageNum);
        iPage.setSize(pageSize);
        int pages = (int) Math.ceil((double) iPage.getTotal() / iPage.getSize());
        iPage.setPages(pages);
        System.out.println(pageNum);
        System.out.println(resultList.size());
        List<T> records = resultList.subList((pageNum - 1) * pageSize, Math.min(pageNum * pageSize, resultList.size()));
        iPage.setRecords(records);
        return iPage;
    }

    public static <T> IPage<T> toIPage(PageQueryDto pageQueryDto) {
        IPage<T> iPage = new Page<>(pageQueryDto.getPageNum(), pageQueryDto.getPageSize());
        return iPage;
    }

    public static <M, N> IPage<N> pageFormatTransform(IPage<M> originPage, Class<N> targetType) {
        IPage<N> ipage = new Page<>();
        ipage.setTotal(originPage.getTotal());
        ipage.setSize(originPage.getSize());
        ipage.setPages(originPage.getPages());
        ipage.setCurrent(originPage.getCurrent());
        return ipage;
    }
}
