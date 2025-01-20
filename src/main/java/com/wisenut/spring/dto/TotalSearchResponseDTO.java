package com.wisenut.spring.dto;

import com.wisenut.spring.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalSearchResponseDTO {

    String query;

    SearchVo<Board> board;

    SearchVo<Appr> appr;

}
