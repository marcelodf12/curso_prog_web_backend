package py.com.tdn.reservation_api.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.utils.Pagination;

public class GenericDao<T, K> {

	Logger log = Logger.getLogger(this.getClass());
	
	@PersistenceContext(unitName = "cursoWebPU")
	protected EntityManager em;

	public T create(T entity) {
		em.persist(entity);
		return entity;
	}

	public T update(T entity) {
		return em.merge(entity);
	}

	public void delete(Class<T> type, K id) {
		T entity = findById(type, id);
		if (entity != null)
			em.remove(entity);
	}

	public T findById(Class<T> type, K id) {
		return em.find(type, id);
	}	

	@SuppressWarnings("unchecked")
	public Pagination<T> findByColumnWithPagination(
			Class<T> type,
			String column,
			String filter,
			String orderBy,
			String order,
			int currentPage,
			int pageSize) {
		
		String qSelect = "SELECT e FROM " + type.getSimpleName() + 
				" e WHERE 0 < LOCATE(:filter, TRIM(UPPER(e." + column + "))) " +
				"ORDER BY "+ orderBy + " " + order;
		log.debug(qSelect);
		//SELECT e FROM ClientBean e WHERE 0 < LOCATE("@gmail.com", TRIM(UPPER(e.email))) ORDER BY lastName ASC
		
		String qCount = "SELECT count(e) FROM " + type.getSimpleName() + 
				" e WHERE 0 < LOCATE(:filter, TRIM(UPPER(e." + column + "))) ";
		log.debug(qCount);
		
		Query query = em.createQuery(qSelect);
		
		Query queryCount = em.createQuery(qCount);
		
		queryCount.setParameter("filter", filter.toUpperCase().trim());
		Long totalCount = (Long) queryCount.getSingleResult();
		Integer totalPage = (int) Math.ceil((double)totalCount/(double)pageSize);
		
		int offset = (currentPage - 1) * pageSize;
		query.setParameter("filter", filter.toUpperCase().trim());
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		
		Pagination<T> page = new Pagination<>();
		page.setItems(query.getResultList());
		page.setTotalPage(totalPage);
		page.setPreviousPage(currentPage>1?currentPage-1:null);
		page.setNextPage(currentPage<totalPage?currentPage+1:null);
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		return page;
	}

}
