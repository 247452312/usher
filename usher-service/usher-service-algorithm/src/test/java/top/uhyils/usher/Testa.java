package top.uhyils.usher;

import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年05月30日 13时11分
 */
public class Testa {


    @Test
    void testtt() {
        // [[9,9],[1,10],[1,3],[9,10],[8,8],[1,5],[3,8],[5,5],[1,6],[1,9]]
        int[] ss;
        ss = new Solution().minInterval(new int[][]{{9, 9}, {1, 10}, {1, 3}, {9, 10}, {8, 8}, {1, 5}, {3, 8}, {5, 5}, {1, 6}, {1, 9}}, new int[]{8, 1, 5, 1, 5, 7, 1, 9, 8, 1});
        for (int s : ss) {
            System.out.printf("," + s);
        }
        System.out.println();

    }

    class Solution {

        public int[] minInterval(int[][] intervals, int[] queries) {

            // 可跳跃记录
            int[] jump = new int[10000001];
            // 存储每个节点最小长度
            int[] res = new int[10000001];
            for (int[] interval : intervals) {
                int intLen = interval[1] - interval[0] + 1;

                for (int j = interval[0]; j <= interval[1]; j++) {
                    // 当前区间长度 小于之前的长度
                    if (res[j] == 0 || intLen <= res[j]) {
                        if (jump[j] == 0) {
                            jump[j] = interval[1];
                        } else {
                            jump[j] = Math.min(interval[1], jump[j]);
                        }
                        res[j] = intLen;
                    } else {
                        // 区间长度大于之前的长度,则判断跳跃点与当前区间结束点大小
                        if (jump[j] < interval[1]) {
                            // 区间结束点大于跳跃点,则从跳跃点开始往后继续
                            j = jump[j];
                        } else {
                            // 区间结束点小于等于结束点,则当前区间没有继续的必要
                            break;
                        }
                    }
                }
            }
            int[] result = new int[queries.length];
            for (int i = 0; i < queries.length; i++) {
                int re = res[queries[i]];
                if (re == 0) {
                    result[i] = -1;
                } else {
                    result[i] = re;
                }
            }
            return result;
        }
    }


}
