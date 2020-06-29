package nl.codecat.meter_readings.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, K> {

	protected abstract JpaRepository<T, K> getRepository();

	@Transactional(readOnly = true)
	public Page<T> get(final Pageable pageable) {
		Assert.notNull(pageable, "Pageable can't be null");

		return getRepository().findAll(pageable);
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		return getRepository().findAll();
	}

	@Transactional(readOnly = true)
	public Optional<T> findById(final K id) {
		Assert.notNull(id, "ID of entity to get can't be null");

		return getRepository().findById(id);
	}

	@Transactional(readOnly = true)
	public List<T> findById(final Collection<K> ids) {
		Assert.notNull(ids, "Collection of IDs of entity to get can't be null");
		Assert.noNullElements(ids, "Collection of IDs of entity to get can't have null elements");

		return getRepository().findAllById(ids);
	}

	public T create(final T entity) {
		Assert.notNull(entity, "Entity to create can't be null");

		return getRepository().save(entity);
	}

	public List<T> createAll(final Collection<T> entities) {
		Assert.notNull(entities, "Entities collection to create can't be null");

		final List<T> result = new ArrayList<>(entities.size());
		for (final T entity : entities) {
			result.add(create(entity));
		}

		return result;
	}

	public T update(final T entity) {
		Assert.notNull(entity, "Entity to update can't be null");

		return getRepository().save(entity);
	}

	public List<T> updateAll(final Collection<T> entities) {
		Assert.notNull(entities, "Entities collection to update can't be null");

		final List<T> result = new ArrayList<>(entities.size());
		for (final T entity : entities) {
			result.add(update(entity));
		}

		return result;
	}

	public void delete(final T entity) {
		Assert.notNull(entity, "Entity to delete can't be null");

		getRepository().delete(entity);
	}

	public void deleteAll(final Collection<T> entities) {
		Assert.notNull(entities, "Entities collection to delete can't be null");

		entities.forEach(this::delete);
	}
}
