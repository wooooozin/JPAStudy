package com.example.jpa.extra.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResultResponseBodyItems {

    List<OpenApiResultResponseBodyItemsItem> item;

}
