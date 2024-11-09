package cotato.backend.domains.post.service;

import static cotato.backend.common.exception.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import cotato.backend.domains.post.entity.Post;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class PostServiceImpl implements PostService {

	// 로컬 파일 경로로부터 엑셀 파일을 읽어 Post 엔터티로 변환하고 저장
	public void saveEstatesByExcel(String filePath) {
		try {
			// 엑셀 파일을 읽어 데이터 프레임 형태로 변환
			List<Post> posts = ExcelUtils.parseExcelFile(filePath).stream()
				.map(row -> {
					String title = row.get("title");
					String content = row.get("content");
					String name = row.get("name");

					return new Post(title, content, name);
				})
				.collect(Collectors.toList());

		} catch (Exception e) {
			log.error("Failed to save estates by excel", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}
}
