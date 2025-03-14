package com.dmh.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserBatchDTO {
    private List<UserDTO> users;
}
