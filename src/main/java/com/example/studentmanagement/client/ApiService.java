package com.example.studentmanagement.client;

import com.example.studentmanagement.model.dto.request.PostRequest;
import com.example.studentmanagement.model.dto.response.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
public class ApiService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Autowired
    public ApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PostResponse> getPosts() {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .retrieve()
                .bodyToFlux(PostResponse.class)
                .collectList()
                .block();
    }

    public PostResponse getPostById(Long id) {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/{id}", id)
                .retrieve()
                .bodyToMono(PostResponse.class)
                .block();
    }

    public PostResponse createPost(PostRequest postRequest) {
        return webClient.post()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .bodyValue(postRequest)
                .retrieve()
                .bodyToMono(PostResponse.class)
                .block();
    }

    public PostResponse updatePost(Long id, PostRequest postRequest) {
        return webClient.put()
                .uri("https://jsonplaceholder.typicode.com/posts/{id}", id)
                .bodyValue(postRequest)
                .retrieve()
                .bodyToMono(PostResponse.class)
                .block();
    }

    public void deletePost(Long id) {
        webClient.delete()
                .uri("https://jsonplaceholder.typicode.com/posts/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
