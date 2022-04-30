package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.schema.dsl.DepreciableItemDSL


class EnumValueDSL<T : Enum<T>>(val value: T) : DepreciableItemDSL()