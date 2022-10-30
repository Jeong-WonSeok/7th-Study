
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BJ_21611_마법사상어와블리자드 {

    static int N, M;
    static int map[][], mapIdx[][], magic[][];
    static int result;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        mapIdx = new int[N * N][2];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        result = 0;
        magic = new int[M][2];


        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            magic[i][0] = Integer.parseInt(st.nextToken());
            magic[i][1] = Integer.parseInt(st.nextToken());
        }


        setIdx();
        for(int i = 0; i < M; i++){
            magicMarble(magic[i]);

            move();

            while(true){
                if(!destroy())
                    break;
                else
                    move();
            }

            numReset();
        }


        System.out.println(result);
    }

    //
    private static void numReset() {
        int mapCopy[][] = new int[N][N];

        //변화 mapCopy에 삽입
        int cnt = 1;
        int num = 1;
        int cr = 0, cc = 0, nr = 0, nc = 0;
        for(int i = 1; i < N*N;i++){
            cr = mapIdx[i][0];
            cc = mapIdx[i][0];

            //비어있는 곳이 생기면 break;
            if(map[cr][cc] == 0)
                break;
            if(i != N*N-1){
                nr = mapIdx[i+1][0];
                nc = mapIdx[i+1][1];
            }

            if(i != N*N-1 && map[cr][cc] == map[nr][nc]){
                cnt++;
            }else{
                if(num>=N*N)
                    break;
                int newr = mapIdx[num][0];
                int newc = mapIdx[num][1];
                num++;
                if(num >= N*N)
                    break;
                int newr2 = mapIdx[num][0];
                int newc2 = mapIdx[num][1];
                num++;
                mapCopy[newr][newc] = cnt;
                mapCopy[newr2][newc2] = map[cr][cc];
                cnt = 1;

            }
        }
        for(int i = 0; i < N; i++){
            map[i] = mapCopy[i].clone();
        }
    }

    //연속되는 4개 폭파
    private static boolean destroy() {
        int r = N / 2, c = N / 2;
        int nr = 0, nc = 0;
        int curDir = 3;
        int d = 1;
        int cnt = 0;
        List<int[]> list = new ArrayList<>();
        boolean flag = false;

        while(true){
            if(r == 0 && c == 0) break;

            for(int k = 0; k < 2; k++){
                for(int i = 0; i < d; i++){
                    if(r == 0 && c == 0) return  flag;

                    nr = r + dr[curDir];
                    nc = c + dc[curDir];

                    if( cnt == 0 && map[r][c] != 0 && map[r][c] == map[nr][nc]) {
                        //cnt==0이면 한번에 두 cnt가 생기므로 +2
                        cnt += 2;
                        list.add(new int[] { r, c});
                        list.add(new int[] { nr, nc });
                    }else if( cnt != 0 && map[r][c] != 0 && map[r][c] == map[nr][nc]){
                        cnt++;
                        list.add(new int[] { nr, nc });
                    }

                    //연속이 끊겼을 때
                    if(map[r][c] != map[nr][nc]){

                        if(cnt >= 4){
                            flag = true;
                            for(int j = 0; j< list.size(); j++){
                                //list에서 가져옴
                                int cur[] = list.get(j);
                                int cr = cur[0], cc = cur[1];
                                result += map[cr][cc];

                                map[cr][cc] = 0; //폭발하면 0으로 정리
                            }
                            //초기화
                        }
                        cnt = 0;
                        list.clear();
                    }

                    r = nr;
                    c = nc;
                }

                curDir = nextDir[curDir];
            }
            d++;
        }
        return flag;
    }


    //구슬 이동 시키기
    private static void move() {
        for(int i = 1; i < N * N; i++){
            int cr = mapIdx[i][0];
            int cc = mapIdx[i][1];
            if(map[cr][cc] == 0){

                int next[] = new int[2];
                // i보다 뒤에있는 idx중 0이 아닌 값 찾기
                for (int j = i + 1; j < N * N; j++) {
                    int nnr = mapIdx[j][0];
                    int nnc = mapIdx[j][1];
                    if (map[nnr][nnc] != 0) {
                        next[0] = nnr;
                        next[1] = nnc;
                        break;
                    }
                }

                int nr = next[0];
                int nc = next[1];
                // 현재위치에 다음 값을 넣기
                map[cr][cc] = map[nr][nc];
                map[nr][nc] = 0;
            }
        }
    }

    private static void magicMarble(int[] magic) {
        int nr = N / 2, nc = N / 2;
        //거리만큼 반복문 돌림
        for(int i = 0; i < magic[1]; i++){
            nr += dr[magic[0]];
            nc += dc[magic[0]];
            if(nr < 0 || nr >= N || nc < 0 || nc >= N)
                break;
            map[nr][nc] = 0;
        }
    }

    static int nextDir[] = { 0, 3, 4, 2, 1 }; // 이동 방향

    // 1 ~ 4 상하좌우
    static int[] dr = {0, -1, 1, 0, 0};
    static int[] dc = {0, 0, 0, -1, 1};
    // map idx로 저장하기
    private static void setIdx(){
        int r = N / 2, c = N / 2;
        int nr = 0, nc = 0;
        int curDir = 3; //좌
        int d = 1; // 이동해야하는 양
        int num = 1;

        while(true) {
            for (int k = 0; k < 2; k++){
                for(int  i = 0; i < d; i++){
                    if( r == 0 && c == 0)
                        return;
                    nr = r + dr[curDir];
                    nc = c + dc[curDir];
                    mapIdx[num][0] = nr;
                    mapIdx[num][1] = nc;
                    num++;

                    r = nr;
                    c = nc;

                }
                curDir = nextDir[curDir];
            }
            d++;
        }
    }
}

