import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BJ_15685_드래곤커브 {

    static int n;
    static int c, r;// 시작 위치
    static int d; //방향
    static int g; //세대
    static boolean[][] map = new boolean[101][101];

    static List<Integer> direct; //선 방향 저장

    static int dr[] = {0, -1, 0, 1};
    static int dc[] = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            c = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());

            draw();
        }

        int result = getSquare();
        System.out.println(result);


    }

    private static void draw() {
//        초기화
        direct = new ArrayList<>();
        direct.add(d);

        int dir;

        while (g-- > 0) {
            for (int i = direct.size() - 1; i >= 0; i--) {
                // 세대별로 90도 회전
                dir = (direct.get(i) + 1) % 4;
                direct.add(dir);
            }
        }

        int nc, nr;
        int cc = c;
        int cr = r;

        map[c][r] = true;

        for (int i = 0; i < direct.size(); i++) {
            dir = direct.get(i);

            nc = cc + dc[dir];
            nr = cr + dr[dir];

            map[nc][nr] = true;

            cr = nr;
            cc = nc;
        }
    }

    private static int getSquare() {
        int cnt = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (map[i][j] && map[i + 1][j] && map[i][j + 1] && map[i + 1][j + 1])
                    cnt++;
            }
        }
        return cnt;
    }
}
