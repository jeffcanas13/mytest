package adec.tech.atms.util;

import org.springframework.stereotype.Component;

/**
 * @author Jonathan Leijendekker
 *         Date: 02/20/2016
 *         Time: 6:37 PM
 */
	
@Component
public class Pagination {

    public Integer getOffset(Integer pageNumber, Integer resultSize) {

        return pageNumber == null || pageNumber <= 0 ? 0 : (pageNumber - 1) * resultSize;
    }

}
