package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.QueryParse;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import javax.management.Query;
import java.util.*;

public class WiseSayingService {

    private static WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public boolean remove(int id) {
        return wiseSayingRepository.remove(id);
    }

    public void build() {
        wiseSayingRepository.build();
    }

    public int write(String content, String author) { //명언을 등록하기

        int id = wiseSayingRepository.getLastIdFromFile()+1;
        wiseSayingRepository.setLastIdForFile(id);
        WiseSaying ws = new WiseSaying(id,content,author);
        wiseSayingRepository.write(ws);

        return id;
    }

    public WiseSaying read(int id) {
        return wiseSayingRepository.read(id);
    }


    public List<WiseSaying> list() {
        return wiseSayingRepository.getWiseSayingList();
    }

    public void modify(int id, String content, String author) {
        WiseSaying ws = new WiseSaying(id,content,author);

        wiseSayingRepository.write(ws);
    }

    //return이 null일 경우 잘못된 command
    public QueryParse queryParser(String command) {
        // ex) 목록?keywordType=author&keyword=작자
        command = command.toLowerCase();
        if(!command.contains("?")){ // 목록으로만 검색할 경우 -> 페이지 1로 해서 검색
            return new QueryParse(1,null,null);
        }
        command = command.split("\\?")[1]; //목록 뒤에 있는 단어들만 사용

        //params[0] = keywordType=author, params[1] = keyword=작자
        String[] params = command.split("&");

        int page = 1;
        String keywordType =null;
        String keyword=null;

        // params 쪼개고 keywordType과 keyword입력
        for (String param : params) {
            String key = param.split("=")[0]; //keywordType or keyword or page
            String value = param.split("=")[1]; //value

            if(key.equalsIgnoreCase("keywordtype")){
                keywordType = value;
            }
            else if(key.equalsIgnoreCase("keyword")){
                keyword = value;
            }
            else if(key.equalsIgnoreCase("page")){
                page = Integer.parseInt(value);
            }
        }

        return new QueryParse(page, keywordType, keyword);
    }


    public int countPages(List<WiseSaying> wiseSayingList){
        final int WiseSayingOnPage = 5; // 한 페이지에 있는 wiseSaying 수
        int pageSize;

        //리스트 개수에 따른 페이지 개수 계산
        if (wiseSayingList == null) {
            pageSize = 0;
        } else {
            int total = wiseSayingList.size();
            pageSize = total / WiseSayingOnPage;
            if (total % WiseSayingOnPage > 0) pageSize += 1; // 나머지가 있을 경우 전체 페이지 +1
        }
        return pageSize;
    }


    //keyword가 있으면 참, 없으면 거짓
    public List<WiseSaying> filteringKeyword(List<WiseSaying> wsList, String keywordType, String keyword) {
        List<WiseSaying> wiseSayingList = new ArrayList<>();
        for(WiseSaying ws : wsList){
            if(keywordType.equalsIgnoreCase("author")){
                if(ws.getAuthor().contains(keyword)){
                    wiseSayingList.add(ws);
                }
            }
            else if(keywordType.equalsIgnoreCase("content")){
                if(ws.getContent().contains(keyword)){
                    wiseSayingList.add(ws);
                }
            }
        }
        return wiseSayingList;
    }

    //전체 페이지 개수, 현재 페이지의 wiseSaying 리스트 반환
    //Null 반환하는 경우 -> 요청한 페이지가 전체 페이지 수보다 큰 경우
    public Map<Integer,List<WiseSaying>> search(int page, String keywordType, String keyword) {
        List<WiseSaying> wiseSayingList =wiseSayingRepository.getWiseSayingList(); // 전체 wiseSaying 가져오기
        Collections.reverse(wiseSayingList); //전체 wiseSaying 역순으로 뒤집어서 최신순 정렬
        List<WiseSaying> resultWiseSayingList = new ArrayList<>();

        int pageSize;

        if(keyword==null && keywordType==null){
            pageSize = countPages(wiseSayingList);
            //DEBUG
            //System.out.println("실행됨");

            if(page > pageSize || wiseSayingList.isEmpty())
                return null; //잘못된 페이지 요청

            int start = (page-1)*5;
            int end = Math.min(page*5,wiseSayingList.size());
            for (int i = start; i < end; i++){ //요청한 페이지의 5개의 id를 list에 삽입
                resultWiseSayingList.add(wiseSayingList.get(i));
            }

            Map<Integer,List<WiseSaying>> result =new HashMap<>();
            result.put(pageSize,resultWiseSayingList);
            return result;
        }

        else if (keyword!=null && keywordType!=null){
            List<WiseSaying> filteredWiseSayingList = filteringKeyword(wiseSayingList, keywordType, keyword);
            pageSize = countPages(filteredWiseSayingList);

            if(page > pageSize || filteredWiseSayingList.isEmpty())
                return null;
            int start = (page-1)*5;
            int end = Math.min(page*5,filteredWiseSayingList.size());
            for (int i = start ; i < end; i++){
                resultWiseSayingList.add(filteredWiseSayingList.get(i));
            }

            Map<Integer,List<WiseSaying>> result =new HashMap<>();
            result.put(pageSize,resultWiseSayingList);
            return result;
        }
        return null;
    }
}
