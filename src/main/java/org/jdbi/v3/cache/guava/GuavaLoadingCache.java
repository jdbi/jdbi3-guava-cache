/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.cache.guava;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.jdbi.v3.core.cache.JdbiCache;
import org.jdbi.v3.core.cache.JdbiCacheLoader;

/**
 * Cache implementation using the guava {@link Cache} object.
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public final class GuavaLoadingCache<K, V> implements JdbiCache<K, V> {

    private final LoadingCache<K, Optional<V>> loadingCache;

    GuavaLoadingCache(CacheBuilder<Object, Object> cacheBuilder, JdbiCacheLoader<K, V> cacheLoader) {

        this.loadingCache = cacheBuilder.build(cacheLoaderAdapter(cacheLoader));
    }

    @Override
    public V get(K key) {
        Optional<V> value = loadingCache.getUnchecked(key);

        if (!value.isPresent()) {
            // remove the cached null value again
            loadingCache.invalidate(key);
        }

        return value.orElse(null);
    }

    @Override
    public V getWithLoader(K key, JdbiCacheLoader<K, V> loader) {
        try {
            Optional<V> value = loadingCache.get(key, () -> Optional.ofNullable(loader.create(key)));

            if (!value.isPresent()) {
                // remove the cached null value again
                loadingCache.invalidate(key);
            }

            return value.orElse(null);
        } catch (ExecutionException e) {
            throw new UncheckedExecutionException(e);
        }
    }

    @Override
    public CacheStats getStats() {
        return loadingCache.stats();
    }

    private static <K, V> CacheLoader<K, Optional<V>> cacheLoaderAdapter(JdbiCacheLoader<K, V> cacheLoader) {
        return new CacheLoader<>() {

            @Override
            public Optional<V> load(K key) {
                return Optional.ofNullable(cacheLoader.create(key));
            }
        };
    }


}
