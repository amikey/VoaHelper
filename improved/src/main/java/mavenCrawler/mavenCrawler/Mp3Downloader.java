package mavenCrawler.mavenCrawler;



public class Mp3Downloader implements Runnable {

	private Frontier frontier;

	public Frontier getFrontier() {
		return frontier;
	}

	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

	public Mp3Downloader() {

	}

	public Mp3Downloader(Frontier frontier) {
		this.frontier = frontier;
	}

	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			String str = frontier.getNext();
			System.out.println("URL---->" + str);
			if (str != null) {
				MP3 mus = new MP3(str, Utils.name);
				mus.run();
			} else {
				// 轮询策略 查看 数据库 数据是否存在 ， 应该 设置一定的 时间长度，
				try {
					Thread.sleep(100);
					System.out.println("时间到");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
