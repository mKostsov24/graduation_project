package org.example.service;

import org.example.enums.ModerationStatus;
import org.example.model.*;
import org.example.model.dto.ErrorDTO;
import org.example.model.dto.NewVoteDTO;
import org.example.model.dto.posts.*;
import org.example.repository.*;
import org.example.service.api.PostService;
import org.example.utils.ErrorsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TagToPostRepository tagToPostRepository;
    private final PostVoteRepository postVoteRepository;
    private final SettingServiceImpl settingService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           TagRepository tagRepository, TagToPostRepository tagToPostRepository,
                           PostVoteRepository postVoteRepository, SettingServiceImpl settingService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.tagToPostRepository = tagToPostRepository;
        this.postVoteRepository = postVoteRepository;
        this.settingService = settingService;
    }

    public PostCountDTO getAllPostsWithConditions(int offset, int limit, String mode) {
        List<PostDTO> postDTOList = new ArrayList<>();
        switch (mode) {
            case ("early"):
                postRepository.findAllActive(
                        PageRequest.of(offset, limit, Sort.by("time").ascending()))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                break;
            case ("popular"):
                postRepository.findAllActiveByComments(
                        PageRequest.of(offset, limit))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                break;
            case ("best"):
                postRepository.findAllActiveByVotes(
                        PageRequest.of(offset, limit))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                break;
            default:
                postRepository.findAllActive(
                        PageRequest.of(offset, limit, Sort.by("time").descending()))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                break;
        }
        return new PostCountDTO(
                postDTOList, postRepository.findAllActive(
                Pageable.unpaged())
                .getSize());
    }

    public PostCountDTO getAllPostsByQuery(int offset, int limit, String query) {
        List<PostDTO> postDTOList = new ArrayList<>();
        if (query.trim().length() == 0) {
            postRepository.findAllActive(
                    PageRequest.of(offset, limit, Sort.by("time").descending()))
                    .forEach(posts -> postDTOList.add(new PostDTO(posts)));
            return new PostCountDTO(
                    postDTOList, postRepository.findAllActive(
                    Pageable.unpaged())
                    .getSize());
        } else {
            postRepository.findAllPostsByQuery(
                    query, PageRequest.of(offset, limit, Sort.by("time").descending()))
                    .forEach(posts -> postDTOList.add(new PostDTO(posts)));
            return new PostCountDTO(
                    postDTOList, postRepository.findAllPostsByQuery(
                    query, Pageable.unpaged())
                    .getSize());
        }
    }

    public PostCountDTO getAllPostsByTag(int offset, int limit, String tag) {
        List<PostDTO> postDTOList = new ArrayList<>();
        if (tag.trim().length() == 0) {
            postRepository.findAllActive(
                    PageRequest.of(offset, limit, Sort.by("time").descending()))
                    .forEach(posts -> postDTOList.add(new PostDTO(posts)));
            return new PostCountDTO(
                    postDTOList, postRepository.findAllActive(
                    Pageable.unpaged())
                    .getSize());
        } else {
            postRepository.findAllActiveByTag(tag, PageRequest.of(
                    offset, limit, Sort.by("time").descending()))
                    .forEach(posts -> postDTOList.add(new PostDTO(posts)));
            return new PostCountDTO(
                    postDTOList, postRepository.findAllActiveByTag(
                    tag, Pageable.unpaged())
                    .getSize());
        }
    }

    public PostCountDTO getAllPostsByDate(int offset, int limit, String Date) {
        List<PostDTO> postDTOList = new ArrayList<>();
        if (Date.length() == 0) {
            postRepository.findAllActive(
                    PageRequest.of(offset, limit, Sort.by("time").descending()))
                    .forEach(posts -> postDTOList.add(new PostDTO(posts)));
            return new PostCountDTO(
                    postDTOList, postRepository.findAllActive(
                    Pageable.unpaged())
                    .getSize());
        } else {
            postRepository.findAllPostsByDate(
                    Date, PageRequest.of(offset, limit, Sort.by("time").descending()))
                    .forEach(posts -> postDTOList.add(new PostDTO(posts)));
            return new PostCountDTO(
                    postDTOList, postRepository.findAllPostsByDate(
                    Date, Pageable.unpaged())
                    .getSize());
        }
    }

    public PostCountDTO getAllPostsByEmail(int offset, int limit, String status, String email) {
        List<PostDTO> postDTOList = new ArrayList<>();
        int postDTOListSize;
        switch (status) {
            case ("published"):
                postRepository.findAllPublished(
                        userRepository.findByEmail(email), PageRequest.of(offset, limit, Sort.by("time").descending()))
                        .forEach(
                                posts -> postDTOList.add(new PostDTO(posts)));
                postDTOListSize = postRepository.findAllPublished(
                        userRepository.findByEmail(email), Pageable.unpaged())
                        .getSize();
                break;
            case ("pending"):
                postRepository.findAllPending(
                        userRepository.findByEmail(email), PageRequest.of(offset, limit, Sort.by("time").descending()))
                        .forEach(
                                posts -> postDTOList.add(new PostDTO(posts)));
                postDTOListSize = postRepository.findAllPending(
                        userRepository.findByEmail(email), Pageable.unpaged())
                        .getSize();
                break;
            case ("declined"):
                postRepository.findAllDeclined(
                        userRepository.findByEmail(email), PageRequest.of(offset, limit, Sort.by("time").descending()))
                        .forEach(
                                posts -> postDTOList.add(new PostDTO(posts)));
                postDTOListSize = postRepository.findAllDeclined(
                        userRepository.findByEmail(email), Pageable.unpaged())
                        .getSize();
                break;
            default:
                postRepository.findAllInactive(
                        userRepository.findByEmail(email), PageRequest.of(offset, limit, Sort.by("time").descending()))
                        .forEach(
                                posts -> postDTOList.add(new PostDTO(posts)));
                postDTOListSize = postRepository.findAllInactive(
                        userRepository.findByEmail(email), Pageable.unpaged())
                        .getSize();
                break;
        }
        return new PostCountDTO(
                postDTOList,
                postDTOListSize);

    }

    public ResponseEntity<?> addPost(NewPostDTO postDTO, String email) {
        Map<String, Object> errors = validatePostInputAndGetErrors(postDTO);
        if (errors.size() > 0) {
            return ResponseEntity.ok(new ErrorDTO(false, errors));
        } else {
            Posts newPost = new Posts();
            if (!settingService.getGlobalSettings().isPostPremoderation()) {
                newPost.setModerationStatus(ModerationStatus.ACCEPTED);
            } else {
                newPost.setModerationStatus(ModerationStatus.NEW);
            }
            newPost.setActive(postDTO.isActive());
            newPost.setTime(postDTO.getTime().isBefore(Instant.now()) ? Instant.now() : postDTO.getTime());
            newPost.setTitle(postDTO.getTitle());
            newPost.setText(postDTO.getText());
            newPost.setUser(userRepository.findByEmail(email));

            postRepository.save(newPost);
            int idNewPOst = newPost.getId();
            editTagToPost(idNewPOst, postDTO);

            return ResponseEntity.ok(new ErrorDTO(true, null));
        }
    }

    public ResponseEntity<?> updatePost(NewPostDTO postDTO, Integer id, String email) {
        Map<String, Object> errors = validatePostInputAndGetErrors(postDTO);
        if (errors.size() > 0 || postRepository.findPostById(id) == null || !postRepository.findPostById(id).getUser().getEmail().equals(email)) {
            return ResponseEntity.ok(new ErrorDTO(false, errors));
        } else {

            ModerationStatus moderationStatus = ModerationStatus.NEW;
            if (userRepository.findByEmail(email).isModerator()) {
                moderationStatus = postRepository.findPostById(id).getModerationStatus();
            }

            postRepository.updatePost(moderationStatus, postDTO.getText(), postDTO.getTitle(), postDTO.isActive(), postDTO.getTime(), id);
            editTagToPost(id, postDTO);
            postRepository.findPostById(id).getTagsList().forEach(
                    t -> {
                        if (!postDTO.getTags().contains(t.getName())) {
                            tagToPostRepository.deleteById(
                                    tagToPostRepository.getByTagAndPost(
                                            tagRepository.findByName(t.getName()), postRepository.findPostById(id)).getId());
                        }
                    });
            return ResponseEntity.ok(new ErrorDTO(true, null));
        }
    }

    private void editTagToPost(int id, NewPostDTO postDTO) {
        postDTO.getTags().forEach(
                t -> {
                    if (tagRepository.findByName(t) == null) {
                        Tags newTags = new Tags();
                        newTags.setName(t);
                        tagRepository.save(newTags);
                    }
                    if (tagToPostRepository.getByTagAndPost(
                            tagRepository.findByName(t), postRepository.findPostById(id)) == null) {
                        TagToPost newTagToPost = new TagToPost();
                        newTagToPost.setPost(postRepository.findPostById(id));
                        newTagToPost.setTag(tagRepository.findByName(t));
                        tagToPostRepository.save(newTagToPost);
                    }
                });
    }

    private Map<String, Object> validatePostInputAndGetErrors(NewPostDTO newPostDTO) {
        Map<String, Object> errorsMap = new HashMap<>();
        if (newPostDTO.getText().isEmpty()) {
            errorsMap.put("text", ErrorsList.STRING_POST_TEXT_EMPTY);
        } else if (newPostDTO.getText().length() < 50) {
            errorsMap.put("text", ErrorsList.STRING_POST_TEXT_SHORT);
        }
        if (newPostDTO.getTitle().isEmpty()) {
            errorsMap.put("title", ErrorsList.STRING_POST_TITLE_EMPTY);
        } else if (newPostDTO.getTitle().length() < 3) {
            errorsMap.put("title", ErrorsList.STRING_POST_TITLE_SHORT);
        }
        return errorsMap;
    }

    public Map<String, Boolean> addLikeAndDislike(NewVoteDTO newVoteDTO, String user, int value) {
        Map<String, Boolean> response = new HashMap<>();
        PostVotes currentPostVotes = postVoteRepository.findByPostAndUser(
                postRepository.findPostById(newVoteDTO.getId()), userRepository.findByEmail(user));
        if (currentPostVotes != null
                && currentPostVotes.getValue() == value) {
            response.put("result", false);
            return response;
        } else if (currentPostVotes != null) {
            postVoteRepository.setValue(currentPostVotes.getId(), value);
            response.put("result", true);
            return response;
        }
        PostVotes vote = new PostVotes();
        vote.setPost(postRepository.findPostById(newVoteDTO.getId()));
        vote.setUser(userRepository.findByEmail(user));
        vote.setTime(Instant.now());
        vote.setValue(value);

        postVoteRepository.save(vote);

        response.put("result", true);
        return response;
    }

    public ResponseEntity<?> getByIdNonAuth(int id, String email) {
        if (postRepository.findById(id, Pageable.unpaged()) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body((String
                            .format(ErrorsList.STRING_POST_NOT_FOUND, id))
                    );
        }
        if (postRepository.findPostById(id).getUser().getEmail().equals(email) ||
                userRepository.findByEmail(email).isModerator()) {
            Posts post = postRepository.findPostById(id);
            return ResponseEntity.ok(new PostByIdDTO(post));
        } else {
            postRepository.updateCount(id);
            Posts post = postRepository.findById(id, Pageable.unpaged()).getContent().get(0);
            return ResponseEntity.ok(new PostByIdDTO(post));
        }
    }

    public ResponseEntity<?> getById(int id) {
        if (postRepository.findById(id, Pageable.unpaged()) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body((String
                            .format(ErrorsList.STRING_POST_NOT_FOUND, id))
                    );
        }
        postRepository.updateCount(id);
        Posts post = postRepository.findById(id, Pageable.unpaged()).getContent().get(0);
        post.getCommentsList().sort(new Comparator<PostComments>() {
            public int compare(PostComments one, PostComments two) {
                return Long.compare(
                        two.getTime().getEpochSecond(), one.getTime().getEpochSecond());
            }
        });

        return ResponseEntity.ok(new PostByIdDTO(post));
    }

    public ResponseEntity<?> getModerationPosts(int offset, int limit, String status, String email) {
        List<PostDTO> postDTOList = new ArrayList<>();
        int listSize = 0;
        switch (status) {
            case ("declined"):
                postRepository.findAllPostsByModerationStatusAndUser(ModerationStatus.DECLINED, userRepository.findByEmail(email),
                        PageRequest.of(offset, limit))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                listSize = postRepository.findAllPostsByModerationStatusAndUser(ModerationStatus.DECLINED, userRepository.findByEmail(email),
                        Pageable.unpaged()).getSize();
                break;
            case ("accepted"):
                postRepository.findAllPostsByModerationStatusAndUser(ModerationStatus.ACCEPTED, userRepository.findByEmail(email),
                        PageRequest.of(offset, limit))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                listSize = postRepository.findAllPostsByModerationStatusAndUser(ModerationStatus.ACCEPTED, userRepository.findByEmail(email),
                        Pageable.unpaged()).getSize();
                break;
            default:
                postRepository.findAllPostsByModerationStatus(ModerationStatus.NEW,
                        PageRequest.of(offset, limit, Sort.by("time").ascending()))
                        .forEach(posts -> postDTOList.add(new PostDTO(posts)));
                listSize = postRepository.findAllPostsByModerationStatus(ModerationStatus.NEW,
                        PageRequest.of(offset, limit)).getSize();
                break;
        }
        return ResponseEntity.ok(new PostCountDTO(
                postDTOList, listSize));
    }

    public ResponseEntity<?> moderationPosts(ModerationPostDTO postDTO, String email) {

        if (postRepository.findPostById(postDTO.getPostId()) == null) {
            return ResponseEntity.badRequest().body(new ErrorDTO(false, null));
        }

        if (postDTO.getStatus().equals("accept")) {
            postRepository.updateModerationStatusPost(ModerationStatus.ACCEPTED, userRepository.findByEmail(email), postDTO.getPostId());
        } else {
            postRepository.updateModerationStatusPost(ModerationStatus.DECLINED, userRepository.findByEmail(email), postDTO.getPostId());
        }
        return ResponseEntity.ok(new ErrorDTO(true, null));
    }
}
