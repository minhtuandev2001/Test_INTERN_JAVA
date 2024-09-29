package com.minhtuan.internjava.repository.custom;

import com.minhtuan.internjava.dto.request.FilterProductRequest;
import com.minhtuan.internjava.dto.response.PageResponse;
import com.minhtuan.internjava.dto.response.ProductResponse;
import com.minhtuan.internjava.model.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class FilterProductsRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public static void joinTable(List<Long> colorIds, List<Long> sizeIds, StringBuilder sql){
        if(colorIds != null){
            sql.append(" INNER JOIN product_color p_c ON p.id = p_c.product_id INNER JOIN color c ON p_c.color_id = c.id ");
        }
        if(sizeIds != null){
            sql.append(" INNER JOIN product_size p_s ON p.id = p_s.product_id INNER JOIN size s ON p_s.size_id = s.id ");
        }
    }
    public static void queryString(Long category_id, Long style_id, BigDecimal minPrice, BigDecimal maxPrice, List<Long> colorIds, List<Long> sizeIds, StringBuilder where){
        if(category_id != null){
            where.append(" AND p.category_id = ").append(category_id);
        }
        if(style_id != null){
            where.append(" AND p.style_id = ").append(style_id);
        }

        if(colorIds != null){
            where.append("  AND c.id IN (");
            where.append(colorIds.stream().map(Object::toString).collect(Collectors.joining(",")));
            where.append(") ");
        }
        if(sizeIds != null){
            where.append("  AND s.id IN (");
            where.append(sizeIds.stream().map(Object::toString).collect(Collectors.joining(",")));
            where.append(") ");
        }

        if(minPrice != null){
            where.append(" AND p.price >= ").append(minPrice);
        }
        if(maxPrice != null){
            where.append(" AND p.price <= ").append(maxPrice);
        }
    }

    public PageResponse<?> getAllProductByFilter(int pageIndex, int size, Long category_id, Long style_id,
                                                 List<Long> colorIds, List<Long> sizeIds, BigDecimal minPrice,
                                                 BigDecimal maxPrice){
        //  lấy count product
        StringBuilder sqlCountQuery = new StringBuilder("SELECT count(DISTINCT P.id) FROM product p ");
        joinTable(colorIds, sizeIds, sqlCountQuery);
        StringBuilder where1 = new StringBuilder(" WHERE 1 = 1 ");
        queryString(category_id, style_id, minPrice, maxPrice, colorIds, sizeIds,where1);
        sqlCountQuery.append(where1);
        Query queryCount = entityManager.createNativeQuery(sqlCountQuery.toString());
        Long totalElement = (Long) queryCount.getSingleResult();
        System.out.println(totalElement);
        //  lấy danh sách product
        StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT p.id, p.name, p.rating, p.price, p.discount, p.thumbnail FROM product p ");
        joinTable(colorIds, sizeIds, sqlQuery);
        StringBuilder where2 = new StringBuilder(" WHERE 1 = 1 ");
        queryString(category_id, style_id, minPrice, maxPrice, colorIds, sizeIds,where2);
        sqlQuery.append(where2);
        Query query = entityManager.createNativeQuery(sqlQuery.toString(), ProductResponse.class);
        List productResponses = query.getResultList();

        // trả về page
        Page<?> page = new PageImpl<Object>(productResponses, PageRequest.of(pageIndex, size), totalElement);

        return PageResponse.builder()
                .page(pageIndex)
                .pageSize(size)
                .totalPage(page.getTotalPages())
                .totalElement(totalElement)
                .items(page.stream().toList())
                .build();
    }
}
