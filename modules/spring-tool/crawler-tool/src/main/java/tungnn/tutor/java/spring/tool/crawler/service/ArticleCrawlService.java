package tungnn.tutor.java.spring.tool.crawler.service;

import tungnn.tutor.java.spring.tool.crawler.model.CrawlRequest;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlResult;

public interface ArticleCrawlService {

  /**
   * Xử lý cào dữ liệu cho danh sách các khóa học và trả về thông tin các đường dẫn file bài viết.
   *
   * @param request Chứa danh sách các Course cùng các articleUrls cần cào.
   * @return CrawlResult Chứa thông tin tương ứng của các Course kèm danh sách articlePaths đã tạo.
   */
  CrawlResult crawlArticles(CrawlRequest request);
}
