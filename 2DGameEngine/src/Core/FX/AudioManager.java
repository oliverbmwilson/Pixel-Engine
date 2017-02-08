package Core.FX;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sun.corba.se.spi.activation.InitialNameServicePackage.NameAlreadyBoundHolder;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
	
	private Map<String, AudioClip> audioClips = new HashMap<String, AudioClip>();
	private Map<String, MediaPlayer> mediaClips = new HashMap<String, MediaPlayer>();
	
	public void addClip(String name, String path) {
		
		try {
			
			AudioClip clip = new AudioClip(path);
			audioClips.put(name, clip);
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
	}
	
	public void addMedia(String name, String path) {
		
		try {
			
			Media media = new Media(path);
			MediaPlayer player = new MediaPlayer(media);
			mediaClips.put(name, player);
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
	}
	
	public void playClip(String name) {
		if(audioClips.containsKey(name)) {
			audioClips.get(name).play();
		}
	}
	
	public void playMedia(String name) {
		if(mediaClips.containsKey(name)) {
			mediaClips.get(name).play();
		}
	}
	
	public void stopClip(String name) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).stop();
			
		}
	}
	
	public void stopMedia(String name) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).stop();
		
		}
	}
	
	public void stopAllClips() {
		
		Set<String> clips = audioClips.keySet();
		
		for(String clip : clips) {
			
			audioClips.get(clip).stop();
			
		}
	}
	
	public void stopAllMedia() {
		
		Set<String> players = mediaClips.keySet();
		
		for(String player : players) {
			
			mediaClips.get(player).stop();
			
		}
	}
	
	public void setBalanceClip(String name, double balance) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).setBalance(balance);;
			
		}
	}
	
	public void setBalanceMedia(String name, double balance) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).setBalance(balance);;
			
		}
	}
	
	public void setBalanceAllClips(double balance) {
		
		Set<String> clips = audioClips.keySet();
		
		for(String clip : clips) {
			
			audioClips.get(clip).setBalance(balance);
			
		}
	}
	
	public void setBalanceAllMedia(double balance) {
		
		Set<String> players = mediaClips.keySet();
		
		for(String player : players) {
			
			mediaClips.get(player).setBalance(balance);
			
		}
	}
	
	public void setRateClip(String name, double rate) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).setRate(rate);
			
		}
	}
	
	public void setRateMedia(String name, double rate) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).setRate(rate);
			
		}
	}
	
	public void setRateAllClips(double rate) {
		
		Set<String> clips = audioClips.keySet();
		
		for(String clip : clips) {
			
			audioClips.get(clip).setRate(rate);
			
		}
	}
	
	public void setRateAllMedia(double rate) {
		
		Set<String> players = mediaClips.keySet();
		
		for(String player : players) {
			
			mediaClips.get(player).setRate(rate);
			
		}
	}
	
	public void loopClip(String name) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).setCycleCount(AudioClip.INDEFINITE);
			
		}
	}
	
	public void loopMedia(String name) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).setCycleCount(MediaPlayer.INDEFINITE);;
			
		}
	}
	
	public void stoploopClip(String name) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).setCycleCount(1);
			
		}
	}
	
	public void stoploopMedia(String name) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).setCycleCount(1);;
			
		}
	}
	
	public void setVolumeClip(String name, double volume) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).setVolume(volume);;
			
		}
	}
	
	public void setVolumeMedia(String name, double volume) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).setVolume(volume);;
			
		}
	}
	
	public void setVloumeAllClips(double volume) {
		
		Set<String> clips = audioClips.keySet();
		
		for(String clip : clips) {
			
			audioClips.get(clip).setVolume(volume);
			
		}
	}
	
	public void setVolumeAllMedia(double volume) {
		
		Set<String> players = mediaClips.keySet();
		
		for(String player : players) {
			
			mediaClips.get(player).setVolume(volume);
			
		}
	}
	
	public void setPriorityClip(String name, int priority) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.get(name).setPriority(priority);
			
		}
	}
	
	public void setPriorityAllClips(int priority) {
		
		Set<String> clips = audioClips.keySet();
		
		for(String clip : clips) {
			
			audioClips.get(clip).setPriority(priority);
			
		}
	}
	
	public void pauseMedia(String name) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.get(name).pause();
		
		}
	}
	
	public void pauseAllMedia() {
		
		Set<String> players = mediaClips.keySet();
		
		for(String player : players) {
			
			mediaClips.get(player).pause();
			
		}
	}
	
	public void playNextMedia(String name, String name2) {
		
		if(mediaClips.containsKey(name) && mediaClips.containsKey(name2)) {
			
			mediaClips.get(name).setOnEndOfMedia( new Runnable() {

				@Override
				public void run() {
					mediaClips.get(name2).play();
					
				}
				
			});
		
		}
	}
	
	public void removeClip(String name) {
		
		if(audioClips.containsKey(name)) {
			
			audioClips.remove(name);
		}
	}
	
	public void removeMedia(String name) {
		
		if(mediaClips.containsKey(name)) {
			
			mediaClips.remove(name);
		}
	}
	
	public void clearClips() {
		
		audioClips.clear();
	}
	
	public void clearMedia() {
		
		mediaClips.clear();
	}

}
