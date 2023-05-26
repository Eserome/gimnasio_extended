package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Ejercicio;
import com.hibernate.util.HibernateUtil;


/**
 * Clase que contiene las funciones para manejar los ejercicos en la base de datos
 * @author a026577592b
 *
 */
public class EjercicioDAO {

	
	/**
	 * Funcion que inserta los ejercicios en la base de datos
	 * @param ej Ejercicio a insertar
	 */
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
	
	/**
	 * Funcion para actualizar un ejercicio
	 * @param ej Ejercicio modificado para actualizar
	 */
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
	
	/**
	 * Funcion para eliminar un determinado ejercicio de la base de datos
	 * @param id Id del ejercicio a eliminar
	 */
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
	
	/**
	 * Funcion que selecciona un ejercicio y lo devuelve en basea un id
	 * @param id Id del ejercicio buscado
	 * @return Devuelve el ejercicio buscado con un id
	 */
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
	
	/**
	 * Funcion que devuelve todos los ejercicios ejn lista de la base de datos
	 * @return Lista con todos los ejercicios de la base de datos
	 */
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
