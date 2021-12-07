package com.example.goto24;

import java.util.Random;

import com.example.o.R;

import android.widget.ImageView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PlayActivity extends Activity {

	int[] imageid = { R.drawable.pa1, R.drawable.pa2, R.drawable.pa3,
			R.drawable.pa4, R.drawable.pa5, R.drawable.pa6, R.drawable.pa7,
			R.drawable.pa8, R.drawable.pa9, R.drawable.pa10, R.drawable.pa11,
			R.drawable.pa12, R.drawable.pa13, R.drawable.pb1, R.drawable.pb2,
			R.drawable.pb3, R.drawable.pb4, R.drawable.pb5, R.drawable.pb6,
			R.drawable.pb7, R.drawable.pb8, R.drawable.pb9, R.drawable.pb10,
			R.drawable.pb11, R.drawable.pb12, R.drawable.pb13, R.drawable.pc1,
			R.drawable.pc2, R.drawable.pc3, R.drawable.pc4, R.drawable.pc5,
			R.drawable.pc6, R.drawable.pc7, R.drawable.pc8, R.drawable.pc9,
			R.drawable.pc10, R.drawable.pc11, R.drawable.pc12, R.drawable.pc13,
			R.drawable.pd1, R.drawable.pd2, R.drawable.pd3, R.drawable.pd4,
			R.drawable.pd5, R.drawable.pd6, R.drawable.pd7, R.drawable.pd8,
			R.drawable.pd9, R.drawable.pd10, R.drawable.pd11, R.drawable.pd12,
			R.drawable.pd13 };
	int who;
	int now_sig;
	Random random;
	boolean no_num, no_sig;
	boolean[] occ = new boolean[100];
	int[] cardid = new int[10];
	int[] ans = new int[10];// 记录最终答案符号 0位为0表示无解 1表示+，2表示-，3表示*，4表示/
	double[] orin = new double[10];// 玩家点击扑克牌的数字依次放在1位，2位，3位，4位
	boolean[] flag = new boolean[10];// 记录某张牌是否被占用
	int[] nex = new int[10];// nex数组模拟链表用于输出答案
	boolean have_cal = false;
	boolean first_num = false;
	int last_card = 0;
	boolean be_solved = false;

	TextView tv1 = null;
	TextView tv2 = null;
	TextView tv3 = null;
	TextView tv4 = null;
	TextView tv5 = null;
	TextView tv6 = null;
	TextView tv7 = null;
	ImageView iv1 = null;
	ImageView iv2 = null;
	ImageView iv3 = null;
	ImageView iv4 = null;

	// 转换传参变量
	boolean have_sig = false;
	boolean[] turned = new boolean[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		// 初始化
		ans[0] = ans[1] = ans[2] = ans[3] = ans[4] = 0;
		orin[1] = orin[2] = orin[3] = orin[4] = 0;
		flag[1] = flag[2] = flag[3] = flag[4] = false;
		nex[1] = nex[2] = nex[3] = nex[4] = 0;
		random = new Random();
		for (int i = 0; i < 90; i++)
			occ[i] = false;

		iv1 = (ImageView) findViewById(R.id.imageView1);
		iv2 = (ImageView) findViewById(R.id.imageView2);
		iv3 = (ImageView) findViewById(R.id.imageView3);
		iv4 = (ImageView) findViewById(R.id.imageView4);

		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv1.setImageResource(imageid[who]);
		orin[1] = (who % 13) + 1;
		occ[who] = true;
		cardid[1] = who;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv2.setImageResource(imageid[who]);
		orin[2] = (who % 13) + 1;
		occ[who] = true;
		cardid[2] = who;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv3.setImageResource(imageid[who]);
		orin[3] = (who % 13) + 1;
		occ[who] = true;
		cardid[3] = who;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv4.setImageResource(imageid[who]);
		orin[4] = (who % 13) + 1;
		cardid[4] = who;
		for (int i = 1; i < 90; i++)
			occ[i] = false;

		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5 = (TextView) findViewById(R.id.textView5);
		tv6 = (TextView) findViewById(R.id.textView6);
		tv7 = (TextView) findViewById(R.id.textView7);

		no_num = true;
		no_sig = true;
		for (int i = 1; i < 5; i++)
			turned[i] = false;
		be_solved = false;
	}

	public void back(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}

	public void find_Answer(int pos, int sig, double sum)// pos记录当前位置,sum代表总和
	{
		if (ans[0] == 1)
			return;

		if (sig == 1) {
			sum += orin[pos];
		} else if (sig == 2) {
			sum -= orin[pos];
		} else if (sig == 3) {
			sum *= orin[pos];
		} else if (sig == 4) {
			sum /= orin[pos];
		}

		if (sum == 24 && flag[1] == true && flag[2] == true && flag[3] == true
				&& flag[4] == true) {
			ans[0] = 1;// 标记成功找到
			return;
		}

		for (int i = 1; i <= 4; i++) {
			if (!flag[i] && ans[0] == 0)// 找到下一个可用数
			{
				flag[i] = true;
				nex[pos] = i;
				for (int j = 1; j <= 4; j++)// 遍历符号
				{
					if (ans[0] != 1)
						ans[i] = j;// 记录符号
					find_Answer(i, j, sum);
					if (ans[0] != 1)
						ans[i] = 0;// 回溯

				}
				if (ans[0] != 1) {
					flag[i] = false;// 回溯
					nex[pos] = 0;
				}
			}
		}

	}

	public void print() {
		int pos;
		for (pos = 1; pos <= 4; pos++)
			if (ans[pos] == 0)
				break;

		double tmp = orin[pos];
		int b = (int) orin[nex[pos]];
		double c = tmp;
		if (ans[nex[pos]] == 1) {
			tmp += orin[nex[pos]];
			int d = (int) c;
			tv1.setText("第一步： " + d + " + " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 2) {
			tmp -= orin[nex[pos]];
			int d = (int) c;
			tv1.setText("第一步： " + d + " - " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 3) {
			tmp *= orin[nex[pos]];
			int d = (int) c;
			tv1.setText("第一步： " + d + " x " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 4) {
			tmp /= orin[nex[pos]];
			int d = (int) c;
			tv1.setText("第一步： " + d + " / " + b + " = " + tmp);

		}
		pos = nex[pos];
		b = (int) orin[nex[pos]];
		c = tmp;
		if (ans[nex[pos]] == 1) {
			tmp += orin[nex[pos]];
			tv2.setText("第二步： " + c + " + " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 2) {
			tmp -= orin[nex[pos]];
			tv2.setText("第二步： " + c + " - " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 3) {
			tmp *= orin[nex[pos]];
			tv2.setText("第二步： " + c + " x " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 4) {
			tmp /= orin[nex[pos]];
			tv2.setText("第二步： " + c + " / " + b + " = " + tmp);

		}
		pos = nex[pos];
		b = (int) orin[nex[pos]];
		c = tmp;
		if (ans[nex[pos]] == 1) {
			tmp += orin[nex[pos]];
			tv3.setText("第三步： " + c + " + " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 2) {
			tmp -= orin[nex[pos]];
			tv3.setText("第三步： " + c + " - " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 3) {
			tmp *= orin[nex[pos]];
			tv3.setText("第三步： " + c + " x " + b + " = " + tmp);

		} else if (ans[nex[pos]] == 4) {
			tmp /= orin[nex[pos]];
			tv3.setText("第三步： " + c + " / " + b + " = " + tmp);

		}

	}

	public void solve(View view)// 点击参考答案调用此函数
	{
		if (be_solved == true)
			return;
		tv7.setText("");
		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[1] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(1, 0, orin[1]);
		}

		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[2] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(2, 0, orin[2]);
		}

		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[3] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(3, 0, orin[3]);
		}

		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[4] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(4, 0, orin[4]);
		}

		if (ans[0] == 1)
			print();
		else {
			tv1.setText("the Question");
			tv2.setText("is");
			tv3.setText("UNSOLVABLE!");
		}
		for (int i = 0; i <= 5; i++) {
			ans[i] = nex[i] = 0;
			flag[i] = false;
		}
	}

	public void new_question(View view) {
		tv1.setText("");
		tv2.setText("");
		tv3.setText("");
		tv4.setText("");
		tv5.setText("");
		tv6.setText("");
		tv7.setText("");
		ans[0] = ans[1] = ans[2] = ans[3] = ans[4] = 0;
		orin[1] = orin[2] = orin[3] = orin[4] = 0;
		flag[1] = flag[2] = flag[3] = flag[4] = false;
		nex[1] = nex[2] = nex[3] = nex[4] = 0;

		for (int i = 0; i < 90; i++)
			occ[i] = false;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv1.setImageResource(imageid[who]);
		orin[1] = (who % 13) + 1;
		occ[who] = true;
		cardid[1] = who;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv2.setImageResource(imageid[who]);
		orin[2] = (who % 13) + 1;
		occ[who] = true;
		cardid[2] = who;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv3.setImageResource(imageid[who]);
		orin[3] = (who % 13) + 1;
		occ[who] = true;
		cardid[3] = who;
		do
			who = random.nextInt(52);
		while (occ[who] == true);
		iv4.setImageResource(imageid[who]);
		orin[4] = (who % 13) + 1;
		cardid[4] = who;

		for (int i = 1; i < 90; i++)
			occ[i] = false;
		be_solved = false;
	}

	public void clear_layout(View view) {
		if (be_solved == true)
			return;
		tv7.setText("");
		tv4.setText("");
		tv5.setText("");
		tv6.setText("");
		iv1.setImageResource(imageid[cardid[1]]);
		iv2.setImageResource(imageid[cardid[2]]);
		iv3.setImageResource(imageid[cardid[3]]);
		iv4.setImageResource(imageid[cardid[4]]);
		for (int i = 1; i <= 5; i++)
			turned[i] = false;
		have_sig = false;
		have_cal = false;
		first_num = false;
		no_num = true;
		no_sig = true;
	}

	// 以下为按钮传值函数

	public void setplus(View view) {
		if (be_solved == true)
			return;
		if (first_num == false) {
			tv7.setText("请先选择一张扑克牌！");
			return;
		}
		if (have_cal == false) {
			last_card = 0;
			no_num = true;
		}
		tv7.setText("");
		tv5.setText("+");
		have_sig = true;
		now_sig = 1;
		no_sig = false;
	}

	public void setminus(View view) {
		if (be_solved == true)
			return;
		if (first_num == false) {
			tv7.setText("请先选择一张扑克牌！");
			return;
		}
		if (have_cal == false) {
			last_card = 0;
			no_num = true;
		}
		tv7.setText("");
		tv5.setText("-");
		have_sig = true;
		now_sig = 2;
		no_sig = false;
	}

	public void setmulti(View view) {
		if (be_solved == true)
			return;
		if (first_num == false) {
			tv7.setText("请先选择一张扑克牌！");
			return;
		}
		if (have_cal == false) {
			last_card = 0;
			no_num = true;
		}
		tv7.setText("");
		tv5.setText("x");
		have_sig = true;
		now_sig = 3;
		no_sig = false;
	}

	public void setdiv(View view) {
		if (be_solved == true)
			return;
		if (first_num == false) {
			tv7.setText("请先选择一张扑克牌！");
			return;
		}
		if (have_cal == false) {
			last_card = 0;
			no_num = true;
		}
		tv7.setText("");
		tv5.setText("/");
		have_sig = true;
		now_sig = 4;
		no_sig = false;
	}

	public void setpic1(View view) {
		if (be_solved == true)
			return;
		if (last_card != 0) {
			turned[last_card] = false;
			if (last_card == 1) {
				iv1.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 2) {
				iv2.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 3) {
				iv3.setImageResource(imageid[cardid[last_card]]);
			} else {
				iv4.setImageResource(imageid[cardid[last_card]]);
			}
		}
		tv7.setText("");
		if (turned[1] == true) {
			tv7.setText("请不要重复使用同一张牌");
			return;
		}

		if (have_sig == false) {
			tv4.setText("" + orin[1]);
		} else {
			tv6.setText("" + orin[1]);
		}
		turned[1] = true;
		iv1.setImageResource(R.drawable.beimian);
		no_num = false;
		last_card = 1;
		first_num = true;
	}

	public void setpic2(View view) {
		if (be_solved == true)
			return;
		if (last_card != 0) {
			turned[last_card] = false;
			if (last_card == 1) {
				iv1.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 2) {
				iv2.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 3) {
				iv3.setImageResource(imageid[cardid[last_card]]);
			} else {
				iv4.setImageResource(imageid[cardid[last_card]]);
			}
		}

		tv7.setText("");
		if (turned[2] == true) {
			tv7.setText("请不要重复使用同一张牌");
			return;
		}
		if (have_sig == false) {
			tv4.setText("" + orin[2]);
		} else {
			tv6.setText("" + orin[2]);
		}
		turned[2] = true;
		iv2.setImageResource(R.drawable.beimian);
		no_num = false;
		last_card = 2;
		first_num = true;
	}

	public void setpic3(View view) {
		if (be_solved == true)
			return;
		if (last_card != 0) {
			turned[last_card] = false;
			if (last_card == 1) {
				iv1.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 2) {
				iv2.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 3) {
				iv3.setImageResource(imageid[cardid[last_card]]);
			} else {
				iv4.setImageResource(imageid[cardid[last_card]]);
			}
		}

		tv7.setText("");
		if (turned[3] == true) {
			tv7.setText("请不要重复使用同一张牌");
			return;
		}
		if (have_sig == false) {
			tv4.setText("" + orin[3]);
		} else {
			tv6.setText("" + orin[3]);
		}
		turned[3] = true;
		iv3.setImageResource(R.drawable.beimian);
		no_num = false;
		last_card = 3;
		first_num = true;
	}

	public void setpic4(View view) {
		if (be_solved == true)
			return;
		if (last_card != 0) {
			turned[last_card] = false;
			if (last_card == 1) {
				iv1.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 2) {
				iv2.setImageResource(imageid[cardid[last_card]]);
			} else if (last_card == 3) {
				iv3.setImageResource(imageid[cardid[last_card]]);
			} else {
				iv4.setImageResource(imageid[cardid[last_card]]);
			}
		}

		tv7.setText("");
		if (turned[4] == true) {
			tv7.setText("请不要重复使用同一张牌");
			return;
		}
		if (have_sig == false) {
			tv4.setText("" + orin[4]);
		} else {
			tv6.setText("" + orin[4]);
		}
		turned[4] = true;
		iv4.setImageResource(R.drawable.beimian);
		no_num = false;
		last_card = 4;
		first_num = true;
	}

	public void cal(View view) {
		if (be_solved == true)
			return;
		tv7.setText("");
		if (no_num == true || no_sig == true) {
			tv7.setText("请将表达式补齐!");
			return;
		}
		double a = Double.parseDouble("" + tv4.getText());
		double b = Double.parseDouble("" + tv6.getText());
		if (now_sig == 1) {
			double tmp = a + b;
			tv4.setText("" + tmp);
		} else if (now_sig == 2) {
			double tmp = a - b;
			tv4.setText("" + tmp);
		} else if (now_sig == 3) {
			double tmp = a * b;
			tv4.setText("" + tmp);
		} else {
			double tmp = a / b;
			tv4.setText("" + tmp);
		}

		tv5.setText("");
		tv6.setText("");
		no_num = true;
		no_sig = true;
		last_card = 0;
		have_cal = true;

		if (turned[1] == true && turned[2] == true && turned[3] == true
				&& turned[4] == true) {
			double tmp = Double.parseDouble("" + tv4.getText());
			if (tmp == 24) {
				tv7.setText("恭喜你答对了！\n你可以点击下一题进行新一轮的游戏\n或返回主界面");
				be_solved = true;

			} else {
				tv7.setText("你的计算结果为:\n" + tmp + "\n答案错误，请重试！");
				tv4.setText("");
				tv5.setText("");
				tv6.setText("");
				iv1.setImageResource(imageid[cardid[1]]);
				iv2.setImageResource(imageid[cardid[2]]);
				iv3.setImageResource(imageid[cardid[3]]);
				iv4.setImageResource(imageid[cardid[4]]);
				for (int i = 1; i <= 5; i++)
					turned[i] = false;
				have_sig = false;
				have_cal = false;
				first_num = false;
				no_num = true;
				no_sig = true;
			}
		}
	}

	public void no_ans(View view) {
		if (be_solved == true)
			return;
		tv7.setText("");
		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[1] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(1, 0, orin[1]);
		}

		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[2] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(2, 0, orin[2]);
		}

		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[3] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(3, 0, orin[3]);
		}

		if (ans[0] == 0) {
			flag[1] = flag[2] = flag[3] = flag[4] = false;
			flag[4] = true;
			nex[1] = nex[2] = nex[3] = nex[4] = 0;
			find_Answer(4, 0, orin[4]);
		}

		if (ans[0] == 0) {
			tv7.setText("恭喜你答对了！\n你可以点击下一题进行新一轮的游戏\n或返回主界面");
			be_solved = true;
		} else {
			tv7.setText("答案错误，请重试！");
		}
		for (int i = 0; i <= 5; i++) {
			ans[i] = nex[i] = 0;
			flag[i] = false;
		}
	}
}
