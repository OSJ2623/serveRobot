import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TablePanel extends JPanel{

	Image ImgEmpty = new ImageIcon("./images/table_empty.png").getImage();
	Image ImgSettingDone = new ImageIcon("./images/table_settingDone.png").getImage();
	Image ImgServingDone = new ImageIcon("./images/table_servingDone.png").getImage();
	Image ImgToClean = new ImageIcon("./images/table_toClean.png").getImage();
	int tableN;
	
	public TablePanel(int tableNumber)
	{
		this.tableN = tableNumber;
	}
	
	
	public void paint(Graphics g){
		
//		if () {
//			g.drawImage(ImgToClean, 0, 0, null);	//ġ�����Ҷ�(��׸�)
//		}
		//else
		if (Queueing.isServingDone[tableN]) {
			g.drawImage(ImgServingDone, 0, 0, null);	//�����Ϸ�(���ĳ���)
		}
		else if (Queueing.isSettingDone[tableN]) {
			g.drawImage(ImgSettingDone, 0, 0, null);	//���ÿϷ�(����������)
		}
//		else if () {
//			g.drawImage(ImgEmpty, 0, 0, null);	//�����Ϸ�(���̺�)
//		}
		else {
			g.setColor(Color.WHITE);
	    	g.fillRect(0, 0, 118, 58);	//�մԾ�����
		}

	}

}
