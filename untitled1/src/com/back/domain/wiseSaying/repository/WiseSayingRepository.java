package com.back.domain.wiseSaying.repository;

//하는 일 : 명언 불러오기, 명언 저장하기, 마지막 id 번호 불러오기, 마지막 id 늘리기, 마지막 id 저장하기, 빌드 저장하기,
// 파일 삭제하기

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WiseSayingRepository {

    public WiseSayingRepository() {
    }

    public boolean remove(int id) {
        try {
            File file = new File("./db/wiseSaying/" + id + ".json");
            if (file.exists()) {
                file.delete();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    public void build() {
        try {
            File dir = new File("./db/wiseSaying");
            int lastId = getLastIdFromFile();
            if (!dir.exists()) dir.mkdirs(); // 파일이 없을 경우 생성
            StringBuilder outputString = new StringBuilder("[");
            OutputStream output = new FileOutputStream("./db/wiseSaying/data.json");
            boolean first = true;

            List<WiseSaying> wiseSayingList = getWiseSayingList();
            for (WiseSaying ws : wiseSayingList) {
                if (!first) {
                    outputString.append(",");
                }
                first = false;
                int id = ws.getId();
                String content = ws.getContent();
                String author = ws.getAuthor();

                outputString.append("{\n\t\"id\" : ").append(id).append(",\n\t\"content\":\"").append(content).append("\",\n\t\"author\":\"").append(author).append("\"\n}");
            }
            outputString.append(']');
            //DEBUG
            //System.out.println("빌드 String: " + outputString);
            output.write(outputString.toString().getBytes());
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public int getLastIdFromFile() {
        try {
            File dir = new File("./db/wiseSaying");
            if (!dir.exists()) dir.mkdirs();
            BufferedReader reader = new BufferedReader(new FileReader("./db/wiseSaying/lastId.txt"));
            String line = reader.readLine();
            reader.close();

//            DEBUG
//            System.out.println("가져온 마지막 ID " + line);

            return Integer.parseInt(line);

        } catch (Exception e) {
            e.getStackTrace();

        }
        return 0;
    }

    public void setLastIdForFile(int id) {
        try {
            File dir = new File("./db/wiseSaying");
            if (!dir.exists()) dir.mkdirs();
            OutputStream output = new FileOutputStream("./db/wiseSaying/lastId.txt");

            //DEBUG
//            System.out.println("저장한 마지막 번호 " + number);

            output.write(String.valueOf(id).getBytes());
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public List<WiseSaying> getWiseSayingList() {
        try {
            int lastId = getLastIdFromFile();
            List<WiseSaying> wiseSayingList = new ArrayList<WiseSaying>();
            for (int i = 1; i <= lastId; i++) {
                String fileName = "./db/wiseSaying/" + i + ".json";
                File file = new File(fileName);
                if (file.exists()) {
                    //DEBUG
                    //System.out.println("파일을 찾음, 파일주소 :" + fileName );
                    wiseSayingList.add(readJsonFile(fileName));
                }
            }
            return wiseSayingList;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private WiseSaying readJsonFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }

            String json = sb.toString();

            //DEBUG
            //System.out.println("읽은 json 파일" + json);

            return parseWiseSayings(json);

        } catch (IOException e) {
            System.out.println("에러 발생");
            e.printStackTrace();
            return null;
        }
    }

    private WiseSaying parseWiseSayings(String json) {
        WiseSaying w = new WiseSaying();

        // id
        String idStr = json.split("\"id\"\\s*:\\s*")[1].split(",")[0].trim();
        w.setId(Integer.parseInt(idStr));

        // content
        String contentStr = json.split("\"content\"\\s*:\\s*\"")[1].split("\"")[0];
        w.setContent(contentStr);

        // author
        String authorStr = json.split("\"author\"\\s*:\\s*\"")[1].split("\"")[0];
        w.setAuthor(authorStr);

        //DEBUG
        //System.out.println(w.getNumber() + " " + w.getContent() + " " + w.getAuthor() + " ");
        return w;
    }

    public WiseSaying read(int id) {
        try {
            String fileName = "./db/wiseSaying/" + id + ".json";
            File file = new File(fileName);
            if (file.exists()) {
                //DEBUG
                //System.out.println("파일을 찾음, 파일주소 :" + fileName );
                return readJsonFile(fileName);
            }
            return null;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    public void write(WiseSaying ws) { //수정
        try {
            File dir = new File("./db/wiseSaying");
            if (!dir.exists()) dir.mkdirs(); // 파일이 없을 경우 생성
            OutputStream output = new FileOutputStream("./db/wiseSaying/" + ws.getId() + ".json");
            String outputString = "{\n\t\"id\" : " + ws.getId() + ",\n\t\"content\":\"" + ws.getContent() + "\",\n\t\"author\":\"" + ws.getAuthor() + "\"\n}";

            //DEBUG
            //System.out.println("저장하는 String: " + outputString);

            output.write(outputString.getBytes());
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


}

