package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Ejercicio;
import com.hibernate.util.HibernateUtil;



public class EjercicioDAO {

	
	
	public static void insertEjercicio(Ejercicio ej) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(ej);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static void updateEjercicio(Ejercicio ej) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(ej);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static void deleteEjercicio(int id) {
		Transaction transaction = null;
		Ejercicio ej= null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			ej=session.get(Ejercicio.class, id);
			session.remove(ej);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static Ejercicio selectEjercicioByID(int id) {
		Transaction transaction = null;
		Ejercicio ej = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<Ejercicio> query = session.createQuery("FROM Ejercicio WHERE id = :id",Ejercicio.class);
			query.setParameter("id",id);
			ej=query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return ej;
	}
	
	public static List<Ejercicio> selectAllEjercicios() {
		Transaction transaction = null;
		List<Ejercicio> ejercicios = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			ejercicios=session.createQuery("FROM Ejercicio", Ejercicio.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return ejercicios;
	}
}
