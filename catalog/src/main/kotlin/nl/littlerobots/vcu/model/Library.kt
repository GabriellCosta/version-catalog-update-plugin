/*
* Copyright 2021 Hugo Visser
*
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
package nl.littlerobots.vcu.model

data class Library(
    val module: String,
    override val version: VersionDefinition
) : HasVersion {
    constructor(
        group: String,
        name: String,
        version: VersionDefinition
    ) : this("$group:$name", version)

    val group: String by lazy {
        module.split(':').dropLast(1).joinToString()
    }
    val name: String by lazy {
        module.split(':').last()
    }
}

fun Library.resolveSimpleVersionReference(versionCatalog: VersionCatalog): VersionDefinition? {
    return when (version) {
        is VersionDefinition.Simple -> version
        is VersionDefinition.Reference -> {
            versionCatalog.versions[version.ref]
        }
        // complex version
        else -> null
    }
}
