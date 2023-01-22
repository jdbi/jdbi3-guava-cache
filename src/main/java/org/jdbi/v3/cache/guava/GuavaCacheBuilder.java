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

import com.google.common.cache.CacheBuilder;
import org.jdbi.v3.core.cache.JdbiCache;
import org.jdbi.v3.core.cache.JdbiCacheBuilder;
import org.jdbi.v3.core.cache.JdbiCacheLoader;

/**
 * Cache builder using the guava {@link com.google.common.cache.Cache} object.
 */
public final class GuavaCacheBuilder implements JdbiCacheBuilder {

    private final CacheBuilder<Object, Object> cacheBuilder;

    /**
     * Returns a new {@link JdbiCacheBuilder} which can be used to construct the internal caches.
     *
     * @return A {@link JdbiCacheBuilder} instance.
     */
    public static JdbiCacheBuilder instance() {
        return new GuavaCacheBuilder();
    }

    /**
     * Wraps an existing {@link CacheBuilder} for use with Jdbi.
     *
     * @param cacheBuilder A {@link CacheBuilder} instance.
     */
    public GuavaCacheBuilder(CacheBuilder<Object, Object> cacheBuilder) {
        this.cacheBuilder = cacheBuilder;
    }

    GuavaCacheBuilder() {
        this.cacheBuilder = CacheBuilder.newBuilder().recordStats();
    }

    @Override
    public <K, V> JdbiCache<K, V> build() {
        return new GuavaCache<>(cacheBuilder);
    }

    @Override
    public <K, V> JdbiCache<K, V> buildWithLoader(JdbiCacheLoader<K, V> cacheLoader) {
        return new GuavaLoadingCache<>(cacheBuilder, cacheLoader);
    }

    @Override
    public JdbiCacheBuilder maxSize(int maxSize) {
        cacheBuilder.maximumSize(maxSize);
        return this;
    }
}
