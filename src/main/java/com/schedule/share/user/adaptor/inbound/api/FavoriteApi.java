package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.common.model.ResponseModel;
import com.schedule.share.common.util.JwtUtil;
import com.schedule.share.user.adaptor.inbound.api.dto.FavoriteRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.FavoriteResponseDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.FavoriteDTOMapper;
import com.schedule.share.user.application.port.inbound.FavoriteCommand;
import com.schedule.share.user.application.port.inbound.FavoriteQuery;
import com.schedule.share.user.application.service.user.FavoriteService;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "즐겨찾기", description = "즐겨찾기 API")
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteCommand favoriteCommand;
    private final FavoriteQuery favoriteQuery;
    private final FavoriteDTOMapper favoriteDTOMapper;
    private final FavoriteService favoriteService;

    private final JwtUtil jwtUtil;

    @Operation(summary = "즐겨찾기 추가 API", description = "즐겨찾기를 추가한다.")
    @PostMapping
    public ResponseModel<Long> create(@RequestHeader("access_token") String accessToken, @RequestBody FavoriteRequestDTO.Favorite body) {
        jwtUtil.checkToken(accessToken);

        long id = favoriteCommand.create(favoriteDTOMapper.toVo(body));
        return ResponseModel.of(id);
    }

    @Operation(summary = "즐겨찾기 단일 조회 API", description = "즐겨찾기를 하나 조회한다.")
    @GetMapping("/{id}")
    public ResponseModel<FavoriteResponseDTO.Response> get(@RequestHeader("access_token") String accessToken, @PathVariable long id) {
        jwtUtil.checkToken(accessToken);

        FavoriteVO.Favorite favorite = favoriteQuery.get(id);

        return ResponseModel.of(favoriteDTOMapper.toResponseDTO(favorite));
    }

    @Operation(summary = "즐겨찾기 모두 조회 API", description = "즐겨찾기를 모두 조회한다.")
    @GetMapping
    public ResponseModel<List<FavoriteResponseDTO.Response>> getList(@RequestHeader("access_token") String accessToken) {
        long userId = jwtUtil.getUserId(accessToken);

        List<FavoriteVO.Favorite> list = favoriteQuery.list(userId);
        List<FavoriteResponseDTO.Response> favoriteListResponse = list.stream().map(favoriteDTOMapper::toResponseDTO).toList();

        return ResponseModel.of(favoriteListResponse);
    }

    @Operation(summary = "즐겨찾기 삭제 API", description = "즐겨찾기를 삭제한다.")
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("access_token") String accessToken, @PathVariable long id) {
        jwtUtil.checkToken(accessToken);

        favoriteCommand.delete(id);
    }

    @Operation(summary = "즐겨찾기들 삭제 API", description = "즐겨찾기들을 삭제한다.")
    @DeleteMapping
    public void bulkDelete(@RequestHeader("access_token") String accessToken, @RequestBody FavoriteRequestDTO.BulkDelete bulkDelete) {
        jwtUtil.checkToken(accessToken);

        favoriteCommand.delete(bulkDelete.list());
    }
}
