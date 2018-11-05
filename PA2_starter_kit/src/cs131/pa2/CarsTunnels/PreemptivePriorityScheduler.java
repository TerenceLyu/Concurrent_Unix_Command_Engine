package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Log.Log;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PreemptivePriorityScheduler extends Tunnel{
	private Collection<Tunnel> tunnels;
	private Map<Vehicle, Tunnel> map = new HashMap<>();
	private PriorityQueue<Vehicle> queue;
	public PreemptivePriorityScheduler(String name)
	{
		super(name);
	}
	public PreemptivePriorityScheduler(String name, Collection<Tunnel> tunnels, Log log){
		super(name);
		this.tunnels = tunnels;
		this.queue = new PriorityQueue<>(20, (a, b) -> b.getPriority() - a.getPriority());
	}

	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle){
		while (true){
			if(vehicle.toString().contains("AMBULANCE")){
				for(Tunnel tunnel: tunnels){
					if (tunnel.tryToEnterInner(vehicle)){
						map.put(vehicle, tunnel);
						BasicTunnel t = (BasicTunnel) tunnel;
						LinkedList<Vehicle> lane = t.getLane();
						for(Vehicle v: lane){
							if (!v.equals(vehicle)) {
								v.signal();
							}
						}
						return true;
					}
					if (!queue.contains(vehicle)){
						queue.add(vehicle);
					}
					try{
						wait();
					} catch (InterruptedException e){}
				}
			}else{
				//if the priority of the vehicle is not the head of the queue, wait.
				while (vehicle!=queue.peek()){
					try {
						wait();
					}catch (InterruptedException e){}
				}
				for (Tunnel tunnel: tunnels)
				{
					if (tunnel.tryToEnterInner(vehicle)){
						queue.remove(vehicle);
						map.put(vehicle, tunnel);
						return true;
					}
				}
				if (!queue.contains(vehicle)){
					queue.add(vehicle);
				}
				try {
					wait();
				}catch (InterruptedException e){}
			}
		}
	}

	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle){
		map.get(vehicle).exitTunnelInner(vehicle);
		if(vehicle.toString().contains("AMBULANCE")){
			Tunnel tunnel = map.get(vehicle);
			BasicTunnel t = (BasicTunnel) tunnel;
			LinkedList<Vehicle> lane = t.getLane();
			for(Vehicle v: lane){
				v.signal();
			}
		}
		map.remove(vehicle);
		if (queue.size() != 0){
			notifyAll();
		}
		
	}
}

