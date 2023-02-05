[![CI Build with tests](https://github.com/jdbi/jdbi3-guava-cache/actions/workflows/ci.yml/badge.svg)](https://github.com/jdbi/jdbi3-guava-cache/actions/workflows/ci.yml)
[![CD from main branch pushes](https://github.com/jdbi/jdbi3-guava-cache/actions/workflows/cd.yml/badge.svg)](https://github.com/jdbi/jdbi3-guava-cache/actions/workflows/cd.yml)

# Jdbi 3 Cache implementation using the Guava cache

This is an implementation of the [Jdbi Cache API](https://jdbi.org/apidocs/org/jdbi/v3/core/cache/package-summary.html) using the [Google Guava Cache API](https://github.com/google/guava/wiki/CachesExplained).

It is *experimental* and outside the main Jdbi repository because the Guava Cache has a number of known problems and [The guava team does not recommend using it for new implementations](https://guava.dev/releases/31.1-jre/api/docs/com/google/common/cache/CacheBuilder.html).

If you are using Guava caches in your project and want to avoid the caffeine dependency, give this project a try. Otherwise use the built-in LRU cache or use the caffeine cache plugin.


This package is set apart from the [main JDBI](https://github.com/jdbi/jdbi) because it is *experimental* and unsupported.


## Releasing

This package is intended to be released whenever a [main JDBI](https://github.com/jdbi/jdbi) release is done.

- Submit a PR that changes the parent version and the `dep.jdbi3.version` property to the just released main version
- Once the PR passes all tests, merge it
- Run the release steps as described [in the JDBI release document](https://github.com/jdbi/jdbi/blob/master/RELEASE_STEPS.md).

