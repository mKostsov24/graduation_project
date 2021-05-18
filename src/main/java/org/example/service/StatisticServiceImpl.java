package org.example.service;

import org.example.model.Users;
import org.example.model.dto.StatisticDTO;
import org.example.repository.PostRepository;
import org.example.repository.PostVoteRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostVoteRepository postVoteRepository;
    private final SettingServiceImpl settingService;

    @Autowired
    public StatisticServiceImpl(PostRepository postRepository, UserRepository userRepository,
                                PostVoteRepository postVoteRepository, SettingServiceImpl settingService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postVoteRepository = postVoteRepository;
        this.settingService = settingService;
    }

    public ResponseEntity<?> getMyStatistic(String email) {
        Users currentUser = userRepository.findByEmail(email);
        StatisticDTO myStatistic = new StatisticDTO();
        myStatistic.setPostsCount(postRepository.getCountOfUserPosts(currentUser));
        myStatistic.setDislikesCount(postVoteRepository.getCountOfUserVotes(currentUser.getId(), -1));
        myStatistic.setLikesCount(postVoteRepository.getCountOfUserVotes(currentUser.getId(), 1));
        myStatistic.setViewsCount(postRepository.getCountOfUserPostsView(currentUser));
        if (postRepository.getFirstPostOfUser(currentUser) != null) {
            myStatistic.setFirstPublication(postRepository.getFirstPostOfUser(currentUser).get(0).getTime().getEpochSecond());
        } else {
            myStatistic.setFirstPublication(0);
        }
        return ResponseEntity.ok(myStatistic);
    }

    public ResponseEntity<?> getAllStatistic(String email) {
        Users currentUser = userRepository.findByEmail(email);
        if (settingService.getGlobalSettings().isStatisticsIsPublic() || currentUser.isModerator()) {
            StatisticDTO allStatistic = new StatisticDTO();
            allStatistic.setPostsCount(postRepository.getCountOfAllPosts());
            allStatistic.setViewsCount(postRepository.getCountOfAllPostsView());
            if (postRepository.getFirstPostDataOfAll() != null) {
                allStatistic.setFirstPublication(postRepository.getFirstPostDataOfAll().getTime().getEpochSecond());
            } else {
                allStatistic.setFirstPublication(0);
            }
            allStatistic.setDislikesCount(postVoteRepository.getCountOfAllVotes(-1));
            allStatistic.setLikesCount(postVoteRepository.getCountOfAllVotes(1));
            return ResponseEntity.ok(allStatistic);
        }
        return ResponseEntity.badRequest().body("Unauthorized");
    }

    public ResponseEntity<?> getAllStatisticNonAuth() {
        if (settingService.getGlobalSettings().isStatisticsIsPublic()) {
            StatisticDTO allStatistic = new StatisticDTO();
            allStatistic.setPostsCount(postRepository.getCountOfAllPosts());
            allStatistic.setViewsCount(postRepository.getCountOfAllPostsView());
            if (postRepository.getFirstPostDataOfAll() != null) {
                allStatistic.setFirstPublication(postRepository.getFirstPostDataOfAll().getTime().getEpochSecond());
            } else {
                allStatistic.setFirstPublication(0);
            }
            allStatistic.setDislikesCount(postVoteRepository.getCountOfAllVotes(-1));
            allStatistic.setLikesCount(postVoteRepository.getCountOfAllVotes(1));
            return ResponseEntity.ok(allStatistic);
        }
        return ResponseEntity.badRequest().body("Unauthorized");


    }
}
