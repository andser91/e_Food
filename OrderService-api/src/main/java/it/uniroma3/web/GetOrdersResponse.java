package it.uniroma3.web;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersResponse {
    private List<GetOrderResponse> orders;
}
