package api;

import model.User;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@RestController
public class UserController {

    private final static List<User> userList = new LinkedList<>();

    /** 현재시간 반환
     * @return 현재시간
     */
    @GetMapping("/")
    public String getCurrTime(){
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("hh;mm;;ss a");
        return time.format(today);
    }

    /** 모든 회원리스트 조회
     * @return 회원리스트
     */
    @GetMapping("/users")// == requestMappping(method = 'get',value='useres')
    public List<User> getUserList(){
        return userList;
    }




    /** 현재리스트에서 같은 이름의 회원이 있는지 검색,있으면 회원 데이터반환,없으면 없습니다 반환
     * @return 회원데이터
     */
    //밑에의 shortcut version이 얘다 @GetMapping("/users")
    @RequestMapping(method=RequestMethod.GET, value = "/users")
    public void getUserName(
            @RequestParam(value="name") final String name){

        int count = 0;//같은이름을 가진 회원들이 몇명인지 저장하는 변수

        for(int i=0;i<userList.size(); i++){
            if(userList.get(i).getName().equals(name)) {
                count++;
                System.out.println(userList.get(i).toString());//이름이 같은 회원들의 회원데이터 모두 출력
            }
            }
        if(count>0) return;//회원의 데이터 반환완료,메소드 종료
        System.out.println("회원이 없습니다");
    }





    /** 현재리스트에서 같은 파트의 회원이 있는지 검색,있으면 회원 데이터반환,없으면 없습니다 반환
     * @return 회원데이터
     */
    @GetMapping("/users")
    public void getUserPart(
            @RequestParam(value="part") final String part){

        /**위에 처럼 userlist를 인덱스로 접근하는거말고 iterator을 사용하면 컬렉션의 구현방법 노출 안시킬 수 있음*/
        Iterator<User> iter = userList.iterator();
        int count = 0;//같은파트인 회원들이 몇명인지 저장하는 변수

        while(iter.hasNext()){
            User next = iter.next();
            if(next.getPart().equals(part)){
                count++;
                System.out.println(next.toString());//파트가 같은 회원들의 회원데이터 모두 출력
            }
        }
        if(count>0) return;//회원의 데이터 반환완료,메소드 종료
        System.out.println("회원이 없습니다"); //같은파트인 회원이 없으면 회원없다고 출력

    }




    /** 현재리스트에서 회원id로 회원검색,있으면 회원 데이터반환,없으면 없습니다 반환
     * @return 회원데이터
     */
    @GetMapping("/users/{user_idx}")
    public String getUserIdx(
            @PathVariable(value="user_idx") final int user_idx){
        Iterator<User> iter = userList.iterator();

        while(iter.hasNext()){   //userlist에서 이터레이터로 하나씩 넘기면서 반복한다
            User next = iter.next();
            if(next.getUser_idx() == user_idx)//리스트에서 해당 회원 아이디가 존재한다면
                return next.toString();//회원의 데이터 반환 ,메소드 종료
        }
        return "회원이 없습니다.";

    }



    /**회원 저장하기*/
    @PostMapping("/users")
    public String addUser(@RequestBody final User user){

        User new_user = new User();

        new_user.setName(user.getName());
        new_user.setUser_idx(user.getUser_idx());
        new_user.setPart(user.getPart());

        userList.add(new_user);

        return "저장완료";
    }

    /**회원정보 수정하기*/
    @PutMapping("/users/{user_idx}")
    public String updateUser(
            @PathVariable(value="user_idx") final int user_idx,
            @RequestBody final User user){

        Iterator<User> iter = userList.iterator(); //이터레이터 선언

        while(iter.hasNext()) {    //userlist에서 이터레이터로 하나씩 넘기면서 반복한다

            User next = iter.next(); //이터레이터가 가리키는 현재 user객체를 받아온다
            if (next.getUser_idx() == user_idx){   // 기존 리스트에서 해당 회원 아이디를 가진 회원 찾는다
                next.setUser_idx(user.getUser_idx()); //정보를 수정한다
                next.setName(user.getName());
                next.setPart(user.getPart());
            }
        }
        return "수정완료";
    }

    /**회원정보 삭제하기*/
    @DeleteMapping("/users/{user_idx}")
    public String updateUser(
            @PathVariable(value="user_idx") final int user_idx){

        Iterator<User> iter = userList.iterator(); //이터레이터 선언

        while(iter.hasNext()) {    //userlist에서 이터레이터로 하나씩 넘기면서 반복한다

            User next = iter.next(); //이터레이터가 가리키는 현재 user객체를 받아온다
            if (next.getUser_idx() == user_idx){   // 기존 리스트에서 해당 회원 아이디를 가진 회원 찾는다
                iter.remove(); //그 회원을 삭제한다
            }
        }
        return "삭제완료";
    }

}
