package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Log.Log;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityScheduler extends Tunnel{
	private Collection<Tunnel> tunnels;
	private Map<Vehicle, Tunnel> map = new HashMap<>();
	private PriorityQueue<Vehicle> queue;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public PriorityScheduler(String name) {
		super(name);
	}
	public PriorityScheduler(String name, Collection<Tunnel> tunnels, Log log){
		super(name, log);
		this.tunnels = tunnels;
		this.queue = new PriorityQueue<>(20, (a, b) -> b.getPriority() - a.getPriority());
	}
	
	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
		lock.lock();
		try {
			if (!queue.contains(vehicle)){
					queue.add(vehicle);
			}
			while (true){
				
				//if the priority of the vehicle is not the head of the queue, wait.
				while (vehicle!=queue.peek()){
					try {
						condition.await();
					}catch (InterruptedException e){}
				}
				for (Tunnel tunnel: tunnels)
				{
					if (tunnel.tryToEnter(vehicle)){
						queue.remove(vehicle);
						map.put(vehicle, tunnel);
						return true;
					}
				}
				try {
					condition.await();
				}catch (InterruptedException e){}
				
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle) {
		lock.lock();
		try {
			map.get(vehicle).exitTunnel(vehicle);
			map.remove(vehicle);
			if (queue.size()!=0){
				condition.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}
}
