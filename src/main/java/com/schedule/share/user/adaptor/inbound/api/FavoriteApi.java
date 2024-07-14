package com.schedule.share.user.adaptor.inbound.api;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "즐겨찾기", description = "즐겨찾기 API")
@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteCommand favoriteCommand;
    private final FavoriteQuery favoriteQuery;
    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 추가 API", description = "즐겨찾기를 추가한다.")
    @PostMapping
    public void create(@RequestBody FavoriteRequestDTO.Favorite body) {
        favoriteCommand.create(FavoriteDTOMapper.INSTANCE.toVo(body));
    }

    @Operation(summary = "즐겨찾기 단일 조회 API", description = "즐겨찾기를 하나 조회한다.")
    @GetMapping("/{id}")
    public FavoriteResponseDTO.Response get(@PathVariable long id) {
        FavoriteVO.Favorite favorite = favoriteQuery.get(id);

        return FavoriteDTOMapper.INSTANCE.toResponseDTO(favorite);
    }

    @Operation(summary = "즐겨찾기 모두 조회 API", description = "즐겨찾기를 모두 조회한다.")
    @GetMapping
    public List<FavoriteResponseDTO.Response> getList() {
        List<FavoriteVO.Favorite> list = favoriteQuery.list();

        return list.stream().map(FavoriteDTOMapper.INSTANCE::toResponseDTO).toList();
    }

    @Operation(summary = "즐겨찾기 수정 API", description = "즐겨찾기를 수정한다.")
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody FavoriteRequestDTO.Favorite body) {
        favoriteCommand.update(id, FavoriteDTOMapper.INSTANCE.toVo(body));
    }

    @Operation(summary = "즐겨찾기 삭제 API", description = "즐겨찾기를 하나 조회한다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        favoriteCommand.delete(id);
    }
}