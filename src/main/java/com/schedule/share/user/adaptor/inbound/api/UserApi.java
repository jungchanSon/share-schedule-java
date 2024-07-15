package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.user.adaptor.inbound.api.dto.UserRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.UserResponseDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.UserDTOMapper;
import com.schedule.share.user.application.port.inbound.UserCommand;
import com.schedule.share.user.application.port.inbound.UserQuery;
import com.schedule.share.user.application.service.user.UserService;
import com.schedule.share.user.application.service.user.vo.UserVO;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "유저", description = "유저 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserQuery userQuery;
    private final UserCommand userCommand;
    private final UserDTOMapper userDTOMapper;
    private final UserService userService;

    // 가입
    @Operation(summary = "유저 가입 API", description = "유저를 가입한다.")
    @PostMapping
    public void create(@RequestBody UserRequestDTO.User body) {
        UserVO.Save vo = userDTOMapper.toVO(body);
        userCommand.create(vo);
    }

    // 한명 조회
    @Operation(summary = "유저 단일 조회 API", description = "id로 유저 한명을 조회한다.")
    @GetMapping("/{id}")
    public UserResponseDTO.Response get(@PathVariable long id) {
        UserVO.User user = userQuery.get(id);

        return userDTOMapper.toResponseDTO(user);
    }

    // 모두 조회
    @Operation(summary = "유저 모두 조회 API", description = "모든 유저를 조회한다.")
    @GetMapping
    public List<UserResponseDTO.Response> getList() {
        List<UserVO.User> list = userQuery.list();

        return list.stream().map(
                userDTOMapper::toResponseDTO
        ).toList();
    }

    // 수정
    @Operation(summary = "유저 수정 API", description = "유저 정보를 수정한다.")
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody UserRequestDTO.User body) {
        UserVO.Save vo = userDTOMapper.toVO(body);
        userCommand.update(id, vo);
    }

    // 탈퇴
    @Operation(summary = "유저 탈퇴 API", description = "유저를 탈퇴시킨다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userCommand.delete(id);
    }
}
